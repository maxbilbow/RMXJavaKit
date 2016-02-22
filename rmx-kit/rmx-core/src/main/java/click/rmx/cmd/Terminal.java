package click.rmx.cmd;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by bilbowm (Max Bilbow) on 20/11/2015.
 */
public interface Terminal<T>
{

  default String cmdPrefix()
  {
    return "/";
  }

  default String argPrefix()
  {
    return "-";
  }

  default boolean isValidCommand(String cmd)
  {
    return !cmd.contains(" ");
  }

  default String getCmd(String cmd, String prefix)
  {
    if (!cmd.startsWith(prefix))
      throw new IllegalArgumentException("'" + cmd + "' does not contain the prefix: '" + prefix + "'");
    cmd = cmd.substring(prefix.length());
    if (!isValidCommand(cmd))
      throw new IllegalArgumentException("'" + cmd + "' Should not contain spaces!");
    return cmd.toLowerCase();
  }

  default boolean isCommandPrefixRequired()
  {
    return cmdPrefix() != null && !cmdPrefix().isEmpty();
  }

  default boolean isArgumentPrefixRequired()
  {
    return argPrefix() != null && !argPrefix().isEmpty();
  }

  default String getCmd(String cmd)
  {
    return isCommandPrefixRequired() ? getCmd(cmd, cmdPrefix()) : cmd;
  }

  default String getArg(String arg)
  {
    return isArgumentPrefixRequired() ? getCmd(arg, argPrefix()) : arg;
  }

  /**
   * Send and unformatted command via a String
   * both the cmd and args are converted to lowercase
   * to allow for simple switch statements to be used
   *
   * @param message "/cmd -arg message"
   * @return
   */
  default T sendCommand(String message)
  {
    if (message == null)
    {
      return sendMessage("");
    }
    if (isCommandPrefixRequired() && !message
            .toLowerCase()
            .startsWith(cmdPrefix().toLowerCase())) //is this just a message?
    {
      return sendMessage(message);
    }

    final String[] parts = message.split(" ");
    final String cmd = getCmd(parts[0]);
    int msg = 1;
    Set<String> args = new HashSet<>();
    for (int i = msg; i < parts.length; ++i)
    {
      if (!isArgumentPrefixRequired() || parts[i].startsWith(argPrefix()))
      {
        args.add(getArg(parts[i]));
        msg++;
      } else
      {
        break;
      }
    }
    message = "";
    if (isArgumentPrefixRequired())
    {
      for (int i = msg; i < parts.length; ++i)
      {
        message += parts[i];
        if (i < parts.length - 1)
          message += " ";
      }
    }
    else
    {
      parts[0] = "";
      message = String.join(" ",parts);
    }
    final String[] sArgs = new String[args.size()];
    return sendCommand(cmd, message, args.toArray(sArgs));
  }

  T sendMessage(String s);

  /**
   * This method receives arguments processed in {@link #sendCommand(String)}.
   *
   * @param cmd     Should never be null
   * @param args    If cmd is not null, this will never be null.
   * @param message Should never be null.
   * @return
   */
  T sendCommand(String cmd, String message, String... args);
}
