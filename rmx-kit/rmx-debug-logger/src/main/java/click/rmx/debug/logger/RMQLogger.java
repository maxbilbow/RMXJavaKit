package click.rmx.debug.logger;


import com.google.gson.Gson;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by bilbowm on 05/11/2015.
 */
public class RMQLogger implements Logger {
    private String exchangeName = "debug_topic_exchange";
    private String host, virtualHost, username, password;
    private Integer port;// = null;
    private String uri;

    private final String appId;

    public RMQLogger(String appId) {
        this.appId = appId;
    }

    public void send(String message, AMQP.BasicProperties properties, String... routing) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, "topic");

        if (properties == null)
            properties = defaultProperties();


        for (String routingKey : routing) {
            channel.basicPublish(exchangeName, routingKey, properties, message.getBytes());
            System.out.println(" ["+appId+"] Sent '" + routingKey + "':'" + message + "'");
        }
        connection.close();
    }

    private AMQP.BasicProperties makeProperties(Map properties) {
        return defaultProperties();//todo
    }

    @Override
    public String getAppId() {
        return appId;
    }

    @Override
    public void send(Object object, AMQP.BasicProperties properties, String... routing) throws IOException, TimeoutException  {
//        ObjectMapper mapper = new ObjectMapper();
        final Gson gson = new Gson();
        String json = null;
        try {
            json = gson.toJson(object);
        } catch (Throwable e) {//JSonParseException?
            try {
                send("Could not parse as JSON will use toString() instead.", properties,  "debug.warning");
            } catch (Exception e1) {
                e1.printStackTrace();
                json = object.toString();
            }
        }
        send(json, null, routing);
    }

    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }
}
