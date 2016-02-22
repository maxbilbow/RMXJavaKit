package click.rmx.cmd;

import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max on 22/02/2016.
 */
public abstract class AbstractTerminal<T,E> implements Terminal<T>
{
  private Map<String,Command<T>> mCommands = new HashMap<>();

  protected Logger mLogger = Logger.getLogger(getClass());

  @PostConstruct
  public abstract AbstractTerminal init();

  public AbstractTerminal<T,E> addCommand(final String aCommand,
                                        final String aDescription,
                                        final Command.Execution<T,E> aExecution)
  {
    mCommands.put(aCommand.toLowerCase(), new Command<T>()
    {

      @Override
      public String getCommand()
      {
        return aCommand;
      }

      @Override
      public String getDescription()
      {
        return aDescription;
      }

      @Override
      public String[] getArgs()
      {
        return new String[0];
      }

      @Override
      public <Ex> T invoke(String[] args, String aMessage, Ex aObject) throws Exception
      {
        return aExecution.invoke(aMessage, (E) aObject,args);
      }
    });
    return this;
  }

  @Override
  public abstract T sendMessage(String s);

  @Override
  public T sendCommand(String cmd, String message, String... args)
  {
    try
    {
      return invoke(cmd.toLowerCase(), message, args, (E) this);
    } catch (Exception aE)
    {
      aE.printStackTrace();
      return onError(aE,cmd,args,message);
    }
  }

  protected abstract T onError(Exception aE, String aCmd, String[] aArgs, String aMessage);

  public Map<String,Command<T>> getCommands()
  {
    return mCommands;
  }

  protected T invoke(String cmd, String aMessage, String[] aArgs, E aTerminal) throws Exception
  {
    if (mCommands.containsKey(cmd))
    {
      return mCommands.get(cmd).invoke(aArgs,aMessage,aTerminal);
    }
    else
    {
      mLogger.info("No command found that matches "+cmd);
      return getHelp();// : null;
    }
  }

  protected String listCommands()
  {
    String help = "Commands:";

    for (Command<T> cmd:mCommands.values())
    {
      help += "\n" + cmd.getCommand() + " - " + cmd.getDescription();
    }
    return help;
  }
  public abstract T getHelp();
}
