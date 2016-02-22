package click.rmx.debug.logger.service;

import click.rmx.cmd.AbstractTerminal;
import click.rmx.debug.RMXException;
import click.rmx.debug.logger.LogBuilder;
import click.rmx.debug.logger.model.Log;
import click.rmx.debug.logger.model.LogType;
import click.rmx.util.ObjectInspector;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Map;

import static click.rmx.debug.logger.service.LogService.toHtml;

/**
 * Created by bilbowm (Max Bilbow) on 20/11/2015.
 */
public class DebugServerTerminal extends AbstractTerminal<String,DebugServerTerminal>
{

  private static final String
          SET_NAME = "setname",
          LOG_MESSAGE = "log",
          HELP = "help",
          START_SERVER = "start",
          STOP_SERVER = "stop",
          WHOAMI = "whoami",
          INSPECT = "inspect",
          POST = "post";

  private ObjectInspector mInspector = new ObjectInspector();

  public void setUsername(String username)
  {
    ((Map) properties.get("userProperties")).put("username", username);
  }

//  @FunctionalInterface
//  interface Execution<T,Ex>
//  {
//    T invoke(String cmd, String[] args, String aMessage, Ex aObject);
//  }

  @PostConstruct
  public void init()
  {
    addCommand(SET_NAME, "Change your username", (message,ds,args)->{
      if (message.replace(" ", "").length() == 0)
        return "'" + message + "' is not a valid username.";
      if (message.equals(ds.getUsername()))
        return "Your username is " + ds.getUsername();
      final String oldName = ds.getUsername();
      Log log = new Log("SERVER");
      log.setLogType(LogType.Info);
      ds.setUsername(message);
      log.setMessage("'" + oldName + "' changed their name to '" + message + "'");
      ds.service.saveAndNotify(log);
      return "";
    }).addCommand(LOG_MESSAGE, "-e/-w/-i [message] - Logs a info, warning or error", (message,ds,args)->{
      if (args.length == 0)
        return createNewLog(message, "i");
      String response = "";
      for (String arg : args)
        response += createNewLog(message, arg) + " ";
      return response + "Logged";
    }).addCommand(HELP,"Brings up a list of available commands",((aMessage, aObject, args) -> "Help:\n"+getHelp()));
    addCommand(START_SERVER,"Starts the RabbitMQ server",(aMessage, aObject, args) -> {
      if (service.isActive())
        return "RabbitMQ logger is already " + service.getStatusAsHtml();
      aMessage = "Starting logger... ";
      startServer(true);
      return aMessage + "Server is " + service.getStatusAsHtml();
    }).addCommand(STOP_SERVER,"Stops the RabbitMQ server",(aMessage, aObject, args) -> {
      if (!service.isActive())
        return "RabbitMQ logger is already " + service.getStatusAsHtml();
      aMessage = "Stopping logger... ";
      startServer(false);
      return aMessage + "Server is " + service.getStatusAsHtml();
    }).addCommand(WHOAMI, "Display's your username",(aMessage, aObject, args) ->  aObject.getUsername());

    addCommand(INSPECT, "Inspect an -object if it exists.", (aMessage, aObject, args) -> inspectObject(aMessage, args));

    addCommand(POST,"Post to a url \"/post url\"",(aMessage, aObject, args) -> {
      return aMessage;
    });
  }



  private final Map<String, Object> properties;
  private final LogService service;

  public DebugServerTerminal(Map properties)
  {
    this.properties = properties;
    this.service = (LogService) properties.get("logService");
  }


  public String getUsername()
  {
    return ((Map) properties.get("userProperties")).get("username").toString();
  }


  @Override
  protected String onError(Exception aE, String aCmd, String[] aArgs, String aMessage)
  {
    mLogger.error(aE);
    return aE.toString();
  }

  private String inspectObject(String object, String... args)
  {

    String result = "<div class=\"object-inspection\">";
    Object inspection = null;
    if (object.length() == 0)
      result += "Known available objects: " + mInspector.stringify(properties.keySet());
    else
    {
      if (properties.containsKey(object))
        inspection = properties.get(object);
      else
      {
        for (Field field : this.getClass().getFields())
          if (field.getName().toLowerCase().equals(object))
            try
            {
              inspection = field.get(this);
              break;
            } catch (Exception e)
            {
              result += e.getLocalizedMessage() + "! >> ";
            }
        result += object + " cannot be inspected.";
      }
    }

    if (inspection != null)
    {
      if (args.length > 0)
      {
        result += toHtml(mInspector.inspectObject(inspection, args));
      } else
        result += String.valueOf(mInspector.stringify(inspection));
    }
    return result + "</div>";
  }


  @Override
  public String sendMessage(String message)
  {
    if (message != null && message.length() > 0)
    {
      Log log = new Log(getUsername());
      log.setLogType(LogType.Message);
      log.setMessage(message);
      service.notifySubscribers(log);
    }
    return "";
  }

  private String createNewLog(String msg, String arg)
  {
    final LogBuilder props = LogBuilder.map().sender(getUsername());
    switch (arg)
    {
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

  private boolean startServer(boolean start)
  {
    final LogService service = LogService.getInstance();
    if (start)
    {
      //Start rabbitMQ Debug log receiver
      try
      {
        service.startDebugExchange();
        return true;
      } catch (Exception e)
      {
        service.notifySubscribers(
                service.makeException(
                        RMXException.unexpected(e, "Rabbit Topic logger failed to start.")
                )
        );
      }
    } else
    {
      try
      {
        return service.closeServer();
      } catch (RMXException e)
      {
        service.notifySubscribers(
                service.makeException(e)//,"Server could not be closed! >> ")
        );
      }
    }
    return false;
  }


  @Override
  public String getHelp()
  {
    return listCommands();
  }
}
