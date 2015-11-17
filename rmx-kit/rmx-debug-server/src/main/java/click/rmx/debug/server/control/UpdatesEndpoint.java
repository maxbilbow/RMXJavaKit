package click.rmx.debug.server.control;

import click.rmx.debug.Bugger;
import click.rmx.debug.RMXException;
import click.rmx.debug.server.service.LogService;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

//import static click.rmx.debug.Bugger.print;

/**
 * Created by bilbowm on 27/10/2015.
 */
@ServerEndpoint(value = "/updates")
public class UpdatesEndpoint {

    private Session session;

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        LogService service = LogService.getInstance();
        if (service == null) {
            throw new NullPointerException("LogService Was Not initialized");
        }

        this.session = session;

        service.addClient(this);
    }

    @OnMessage
    public String echo(String message) {
        boolean isActive = LogService.getInstance().isActive();
        if (message.toLowerCase().startsWith("start"))
            if (!isActive) {
                message = "Starting server... ";
                isActive = startServer(true);
            } else {
                message = "";
            }
        else if (message.toLowerCase().startsWith("stop"))
            if (isActive) {
                message = "Stopping server... ";
                isActive = !startServer(false);
            } else {
                message = "";
            }
        else if (message.toLowerCase().startsWith("help"))
            message = getHelp();
        else
            message = "\"" + message + "\" not recognised. ";

        String state = isActive ?
                "<span style=\"color:green\">ON</span>" :
                "<span style=\"color:red\">OFF</span>";
        return message + " >> Server is " + state;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
//        print("DISCONNECTED");
//        RemoteEndpoint.Basic remoteEndpointBasic = session.getBasicRemote();
        LogService.getInstance().removeClient(this);

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
//        print(error);
    }

    public void broadcast(String message) throws IOException {
        this.session.getAsyncRemote().sendText(message, Bugger::print);
    }

    private boolean startServer(boolean start) {
        final LogService service = LogService.getInstance();
        if (start) {
            //Start rabbitMQ Debug log receiver
            try {
                service.startDebugExchange();
                return true;
            } catch (Exception e) {
                service.notifySubscribers(
                        service.makeException(
                                RMXException.unexpected(e, "Rabbit Topic server failed to start.")
                        )
                );
            }
        } else {
            try {
                return service.closeServer();
            } catch (RMXException e) {
                service.notifySubscribers(
                        service.makeException(e)//,"Server could not be closed! >> ")
                );
            }
        }
        return false;
    }

    private String getHelp() {
        String help = " -- HELP --" +
                "<br/><strong> Available Commands:</strong>";
        help += "<br/> HELP - shows this message";
        help += "<br/> START - starts the server";
        help += "<br/> STOP - stops the server";
        help += "<br/>";
        return help;
    }
}