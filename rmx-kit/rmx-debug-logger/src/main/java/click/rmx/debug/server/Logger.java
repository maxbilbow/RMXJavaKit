package click.rmx.debug.server;

import com.rabbitmq.client.AMQP;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * Created by bilbowm on 27/10/2015.
 */
public interface Logger {


    default AMQP.BasicProperties defaultProperties()
    {
        return new AMQP.BasicProperties.Builder()
            .appId(getAppId())
            .timestamp(new Date())
            .build();
    }

    String getAppId();


    /**
     *
     * @param object
     * @param properties
     * @param routing
     * @throws Exception
     */
    void send(Object object, AMQP.BasicProperties properties, String... routing) throws IOException, TimeoutException ;

    /**
     *
     * @param message
     * @param properties
     * @param routing
     * @throws Exception
     */
    void send(String message, AMQP.BasicProperties properties, String... routing)
            throws IOException, TimeoutException;

    /**
     *
     * @param message
     * @param routing
     * @throws Exception
     */
    default void send(String message, String... routing) throws IOException, TimeoutException {
        send(message, defaultProperties(), routing);
    }

    /**
     *
     * @param object
     * @param routing
     * @throws Exception
     */
    default void send(Object object, String... routing) throws IOException, TimeoutException
    {
        send(object, defaultProperties(), routing);
    }

    default boolean logWarning(String message)
    {
        try {
            send(message, "debug.warning");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    default boolean logException(String message)
    {
        try {
            send(message, "debug.error");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    default boolean logMessage(String message)
    {
        try {
            send(message, "debug.log");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    default boolean logWarning(Object object)
    {
        try {
            send(object, "debug.warning");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    default boolean logException(Object object)
    {
        try {
            send(object, "debug.error");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    default boolean logMessage(Object object)
    {
        try {
            send(object, "debug.log");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    void setHost(String host);

    void setPort(Integer port);

    void setUri(String uri);

    void setVirtualHost(String virtualHost);

    void setUsername(String username);

    void setPassword(String password);
}