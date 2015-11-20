package click.rmx.debug.logger.model;

/**
 * Created by Max on 25/10/2015.
 */
public enum LogType {

    Message("updates"), Info("debug.log"), Warning("debug.warning"), Exception("debug.error");
    public final String channel;

    LogType(String channel) {
        this.channel = channel;
    }
}
