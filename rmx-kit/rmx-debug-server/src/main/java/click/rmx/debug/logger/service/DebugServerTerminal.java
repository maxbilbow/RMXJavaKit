package click.rmx.debug.logger.service;

import click.rmx.debug.Bugger;
import click.rmx.debug.RMXException;
import click.rmx.debug.logger.LogBuilder;
import click.rmx.debug.logger.model.Log;
import click.rmx.debug.logger.model.LogType;

import java.lang.reflect.Field;
import java.util.Map;

import static click.rmx.debug.logger.service.LogService.toHtml;

/**
 * Created by bilbowm (Max Bilbow) on 20/11/2015.
 */
public class DebugServerTerminal implements Terminal<String>  {


    private static final String
            SET_NAME = "setname",
            LOG_MESSAGE = "log",
            HELP = "help",
            START_SERVER = "start",
            STOP_SERVER = "stop",
            WHOAMI = "whoami",
            INSPECT = "inspect";

    private static final String AVAILABLE_COMMANDS;

    static {
        String cmds = "<strong> Available Commands:</strong>";
        for (Cmd cmd : Cmd.values())
            cmds += "<br/>"+cmd;
        AVAILABLE_COMMANDS = cmds;
    }

    public void setUsername(String username) {
        ((Map)properties.get("userProperties")).put("username", username);
    }

    enum Cmd {
        SetName(SET_NAME, "Change your username"),
        LogMessage(LOG_MESSAGE, "-e/-w/-i [message] - Logs a info, warning or error"),
        GetHelp(HELP, "Brings up a list of available commands"),
        StartServer(START_SERVER, "Starts the RabbitMQ server"),
        StopServer(STOP_SERVER, "Stops the RabbitMQ server"),
        Whoami(WHOAMI, "Display's your username"),
        Inspect(INSPECT, "Inspect an -object if it exists.");

        private final String cmd, description;

        Cmd(String cmd, String description) {
            this.cmd = cmd;
            this.description = description;
        }

        @Override
        public final String toString() {
            return "/" + cmd + " - " + description;
        }
        //        RESPONSE="<span style=\"color: rgb(151, 253, 255);\">RESPONSE:</span> "
    }


    private final Map<String,Object> properties;
    private final LogService service;
    public DebugServerTerminal(Map properties) {
        this.properties = properties;
        this.service = (LogService) properties.get("logService");
    }



    public String getUsername() {
        return ((Map)properties.get("userProperties")).get("username").toString();
    }

    @Override
    public String sendCommand(String cmd, String message, String... args) {
        switch (cmd) {
            case START_SERVER:
                if (service.isActive())
                    return "RabbitMQ logger is already " + service.getStatusAsHtml();
                message = "Starting logger... ";
                startServer(true);
                return message + "Server is " + service.getStatusAsHtml();
            case STOP_SERVER:
                if (!service.isActive())
                    return "RabbitMQ logger is already " + service.getStatusAsHtml();
                message = "Stopping logger... ";
                startServer(false);
                return message + "Server is " + service.getStatusAsHtml();
            case HELP:
                return "/HELP <br/>" + getHelp();
            case LOG_MESSAGE:
                if (args.length == 0)
                    return createNewLog(message, "i");
                String response = "";
                for (String arg : args)
                    response += createNewLog(message, arg) + " ";
                return response + "Logged";
            case SET_NAME:
                if (message.replace(" ", "").length() == 0)
                    return "'" + message + "' is not a valid username.";
                if (message.equals(getUsername()))
                    return "Your username is " + getUsername();

                final String oldName = getUsername();
                Log log = new Log("SERVER");
                log.setLogType(LogType.Info);
                setUsername(message);
                log.setMessage("'" + oldName + "' changed their name to '" + message + "'");
                service.saveAndNotify(log);
                return "";

            case WHOAMI:
                return getUsername();
            case INSPECT:
                return inspectObject(message, args);
        }
        return "'/" + cmd + "' was not recognised >> " + getHelp();
    }

    private String inspectObject(String object, String... args) {

        String result = "<div class=\"object-inspection\">";
        Object inspection = null;
        if (object.length() == 0)
            result += "Known available objects: " + Bugger.stringify(properties.keySet());
        else {
            if (properties.containsKey(object))
                inspection = properties.get(object);
            else {
                for (Field field : this.getClass().getFields())
                    if (field.getName().toLowerCase().equals(object))
                        try {
                            inspection = field.get(this);
                            break;
                        } catch (Exception e) {
                            result += e.getLocalizedMessage() + "! >> ";
                        }
                result += object + " cannot be inspected.";
            }
        }

        if (inspection != null) {
            if (args.length > 0) {
                result += toHtml(Bugger.inspectObject(inspection, args));
            } else
                result += String.valueOf(Bugger.stringify(inspection));
        }
        return result + "</div>";
    }


    @Override
    public String sendMessage(String message) {
        if (message != null && message.length() > 0) {
            Log log = new Log(getUsername());
            log.setLogType(LogType.Message);
            log.setMessage(message);
            service.notifySubscribers(log);
        }
        return "";
    }

    private String createNewLog(String msg, String arg) {
        final LogBuilder props = LogBuilder.map().sender(getUsername());
        switch (arg) {
            case "e":
                props.logType(LogType.Exception);
                break;
            case "w":
                props.logType(LogType.Warning);
                break;
            case "i":
            default:
                props.logType(LogType.Info);
                break;
        }
        service.processMessage(msg, service.NotifyAndSave, props);
        return props.logType();
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
                                RMXException.unexpected(e, "Rabbit Topic logger failed to start.")
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
        return AVAILABLE_COMMANDS + "<br/> >> Server is " + service.getStatusAsHtml();
    }

}
