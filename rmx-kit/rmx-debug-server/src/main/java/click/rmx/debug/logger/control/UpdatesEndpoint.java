package click.rmx.debug.logger.control;

import click.rmx.debug.Bugger;
import click.rmx.debug.logger.service.DebugServerTerminal;
import click.rmx.debug.logger.service.LogService;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

//import static click.rmx.debug.Bugger.print;

/**
 * Created by bilbowm on 27/10/2015.
 */
@ServerEndpoint(value = "/updates")
public class UpdatesEndpoint {

    private static HashMap<String, Map> userPropertiesStore = new HashMap<>();

    private LogService service;
    private Session session;
    private DebugServerTerminal terminal;
    private String httpSessionId;
    private final Map<String, Object> sessionProperties = new HashMap<>();
    private Map<String, Object> userProperties;

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;
        this.service = LogService.getInstance();
        try {
            httpSessionId = (String) session.getClass().getMethod("getHttpSessionId").invoke(session);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NullPointerException e) {
            service.saveAndNotify(service.makeException(e));
        } finally {
            if (httpSessionId != null && userPropertiesStore.containsKey(httpSessionId))
                userProperties = userPropertiesStore.get(httpSessionId);
            else {
                userProperties = new HashMap<>();
                userProperties.put("username", "User " + session.getId());
            }

            userProperties.put("state", "connected");
            if (httpSessionId != null)
                userPropertiesStore.put(httpSessionId, userProperties);
        }
        sessionProperties.put("this", this);
        sessionProperties.put("endpoint", this);
        sessionProperties.put("endpointConfig", endpointConfig);
        sessionProperties.put("logService", service);
        sessionProperties.put("session", session);
        sessionProperties.put("httpSessionId", httpSessionId);
        sessionProperties.put("userProperties", userProperties);
        sessionProperties.put("sessions", userPropertiesStore);

        this.terminal = new DebugServerTerminal(sessionProperties);

        if (service == null) {
            throw new NullPointerException("LogService Was Not initialized");
        }


        service.saveAndNotify(service.makeLog(terminal.getUsername() + " connected."));
        service.addClient(this);
    }

    @OnMessage
    public String echo(String message) {
        return terminal.sendCommand(message);

    }


    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        String report = terminal.getUsername() + " disconnected.";
        try {
            String rsn = closeReason.getReasonPhrase();
            if (rsn != null && !rsn.isEmpty())
                report += " Reason: " + rsn;
        } catch (Exception e) {
            Bugger.print(e);
        } finally {
            service.removeClient(this);
            service.saveAndNotify(
                    service.makeLog(report)
            );
            userProperties.put("state", "away");
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
        service.save(
                service.makeException(error.toString())
        );
    }


    public void broadcast(String message) throws Exception {
        this.session.getBasicRemote().sendText(message);//, System.out::println);
    }


}