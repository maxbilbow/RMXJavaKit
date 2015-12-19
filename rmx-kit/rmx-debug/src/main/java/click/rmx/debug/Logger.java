package click.rmx.debug;

/**
 * Created by bilbowm (Max Bilbow) on 27/11/2015.
 */
public interface Logger {

    /**
     * @return the Bugger singleton unless overridden
     */
    default Bugger logger()
    {
        return Bugger.getInstance();
    }

    default void logInfo(Object o)
    {
        logger().info(o,1);
    }

    default void logWarning(Object o)
    {
        logger().warn(o,1);
    }

    default void logError(Object o)
    {
        logger().error(o,1);
    }

    default void logInfo()
    {
        logger().info(this,1);
    }

    default void logWarning()
    {
        logger().warn(this,1);
    }

    default void logError()
    {
        logger().error(this,1);
    }
}
