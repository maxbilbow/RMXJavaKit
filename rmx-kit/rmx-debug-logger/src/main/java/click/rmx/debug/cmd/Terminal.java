package click.rmx.debug.cmd;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by bilbowm (Max Bilbow) on 20/11/2015.
 */
public interface Terminal<T> {
    default String cmdPrefix() {
        return "/";
    }

    default String argPrefix() {
        return "-";
    }

    default boolean isValidCommand(String cmd)
    {
        return !cmd.contains(" ");
    }

    default String getCmd(String cmd, String prefix){
        if (!cmd.startsWith(prefix))
            throw new IllegalArgumentException("'" +cmd + "' does not contain the prefix: '" + prefix + "'");
        cmd = cmd.substring(prefix.length());
        if (!isValidCommand(cmd))
            throw new IllegalArgumentException("'" +cmd + "' Should not contain spaces!");
        return cmd.toLowerCase();
    }

    default String getCmd(String cmd){
        return getCmd(cmd, cmdPrefix());
    }

    default String getArg(String arg){
        return getCmd(arg, argPrefix());
    }

    /**
     * Send and unformatted command via a String
     * both the cmd and args are converted to lowercase
     * to allow for simple switch statements to be used
     * @param message "/cmd -arg message"
     * @return
     */
    default T sendCommand(String message) {
        if (message == null)
            return sendMessage("");
        final String[] part = message.split(" ");
        if (part.length == 0 || !part[0].startsWith(cmdPrefix())) //is this just a message?
            return sendMessage(message);

        final String cmd = getCmd(part[0]);
        int msg = 1;
        Set<String> args = new HashSet<>();
        for (int i = msg; i<part.length; ++i){
            if (part[i].startsWith(argPrefix())) {
                args.add(getArg(part[i]));
                msg++;
            } else {
                break;
            }
        }
        message = "";
        for (int i = msg; i < part.length; ++i){
            message += part[i];
            if (i < part.length - 1)
                message += " ";
        }
        final String[] sArgs = new String[args.size()];
        return sendCommand(cmd, message, args.toArray(sArgs));
    }

    T sendMessage(String s);

    /**
     * This method receives arguments processed in {@link #sendCommand(String)}.
     *
     * @param cmd Should never be null
     * @param args If cmd is not null, this will never be null.
     * @param message Should never be null.
     * @return
     */
    T sendCommand(String cmd, String message, String... args);
}
