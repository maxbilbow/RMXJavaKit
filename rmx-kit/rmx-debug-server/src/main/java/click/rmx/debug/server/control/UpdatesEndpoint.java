package click.rmx.debug.server.control;

import click.rmx.debug.Bugger;
import click.rmx.debug.RMXException;
import click.rmx.debug.server.LogBuilder;
import click.rmx.debug.server.model.Log;
import click.rmx.debug.server.model.LogType;
import click.rmx.debug.server.service.LogService;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

//import static click.rmx.debug.Bugger.print;

/**
 * Created by bilbowm on 27/10/2015.
 */
@ServerEndpoint(value = "/updates")
public class UpdatesEndpoint {

    private Session session;

    private String username;

    private LogService service;

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        LogService service = LogService.getInstance();
        if (service == null) {
            throw new NullPointerException("LogService Was Not initialized");
        }

        this.session = session;
        this.service = LogService.getInstance();
        this.username = "User " + session.getId();
        service.saveAndNotify(service.makeLog(username + " connected."));
        service.addClient(this);
    }

    @OnMessage
    public String echo(String message) {
        if (message.startsWith("/")) { //process as command
            return proccessCommand(message.substring(1));
        } else { //Send a message
            Log log = new Log(username);
            log.setLogType(LogType.Message);
            log.setMessage(message);
            service.notifySubscribers(log);
            return "";//"sent: " + message;//username + service.processMessage(message,username,service.Notify).getMessage();
        }
    }

    static final String
            SET_NAME = "setname ",
            LOG_MESSAGE = "log ",
            HELP = "help",
            START_SERVER = "start",
            STOP_SERVER = "stop",
            RESPONSE = "<span style=\"color: rgb(151, 253, 255);\">RESPONSE:</span> "
    ;
    private String proccessCommand(String message) {
        if (message.toLowerCase().startsWith(START_SERVER)) {
            if (service.isActive())
                return "RabbitMQ server is already " + service.getStatusAsHtml();
            message = "Starting server... ";
            startServer(true);
            return message + "Server is " + service.getStatusAsHtml();
        } else if (message.toLowerCase().startsWith(STOP_SERVER)) {
            if (!service.isActive())
                return "RabbitMQ server is already " + service.getStatusAsHtml();
            message = "Stopping server... ";
            startServer(false);
            return message + "Server is " + service.getStatusAsHtml();
        }
        else if (message.toLowerCase().startsWith(HELP))
            return "/HELP <br/>" + getHelp();
        else if (message.toLowerCase().startsWith(LOG_MESSAGE))
            return createNewLog(message.substring(LOG_MESSAGE.length()));
        else if (message.toLowerCase().startsWith(SET_NAME)) {
            this.username = message.substring(SET_NAME.length());
            return "Username is now: " + username;
        }

        return "'/"+message+"' not recognised " + getHelp();
    }

    private String createNewLog(String msg) {
        final LogBuilder props = LogBuilder.map().sender(username);
        if (msg.toLowerCase().startsWith("-")){
            msg = msg.substring(1);

            char code = msg.toLowerCase().charAt(0);
            msg = msg.substring(msg.charAt(1) == ' ' ? 2 : 1);
            switch (code) {
                case 'e':
                    props.logType(LogType.Exception);
                    break;
                case 'w':
                    props.logType(LogType.Warning);
                    break;
                default:
                    props.logType(LogType.Info);
                    break;
            }
        } else {
            props.logType(LogType.Info);
        }
        service.processMessage(msg, service.NotifyAndSave, props);
        return props.logType() + " Logged";
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        String report = username + " disconnected.";
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
        String help =
                "<strong> Available Commands:</strong>";
        help += "<br/> /help - shows this message";
        help += "<br/> /start - starts the RabbitMQ server";
        help += "<br/> /stop - stops the RabbitMQ server";
        help += "<br/> /log -e/-w/-i [message] - Logs a info, warning or error";
        help += "<br/> /setname [name] - change your name" +
                "<br/> >> Server is " + service.getStatusAsHtml();
        return help;
    }
}