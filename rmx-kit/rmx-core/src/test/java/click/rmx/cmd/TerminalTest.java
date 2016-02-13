package click.rmx.cmd;

import click.rmx.util.ObjectInspector;
import org.junit.Test;

/**
 * Created by Max on 13/02/2016.
 */
public class TerminalTest
{

  Terminal<String> mTerminal = new Terminal<String>()
  {
    @Override
    public String sendMessage(String s)
    {
      System.out.println("Received Message: " + s);
      return null;
    }

    @Override
    public String sendCommand(String cmd, String message, String... args)
    {
      System.out.println("Received Command: " + cmd + "\n" +
              "message: " + message + "\n" +
              "args: " + new ObjectInspector().stringify(args));
      return null;
    }

    @Override
    public String cmdPrefix()
    {
      return "/";
    }

    @Override
    public String argPrefix()
    {
      return "-";
    }
  };
  @Test
  public void sendCommand() throws Exception
  {
    mTerminal.sendCommand("/cmd -arg1 -arg2 message message thanks");
  }
}