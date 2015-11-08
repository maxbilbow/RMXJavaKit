package click.rmx.debug.client;

import com.rabbitmq.client.AMQP;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by bilbowm on 05/11/2015.
 */
public class WSLogger implements Logger {

    private final String appId;
    private String endpoint = "ws://localhost:8080/debug-server/listener";

    public WSLogger(String appId)
    {
        this.appId = appId;
    }


    @Override
    public String getAppId() {
        return appId;
    }

    @Override
    public void send(Object object, AMQP.BasicProperties properties, String... routing) throws IOException, TimeoutException {

    }

    @Override
    public void send(String message, AMQP.BasicProperties properties, String... routing) throws IOException, TimeoutException {

    }

    @Override
    public void setHost(String host) {

    }

    @Override
    public void setPort(Integer port) {

    }

    @Override
    public void setUri(String uri) {

    }

    @Override
    public void setVirtualHost(String virtualHost) {

    }

    @Override
    public void setUsername(String username) {

    }

    @Override
    public void setPassword(String password) {

    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
