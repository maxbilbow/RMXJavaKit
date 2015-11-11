package click.rmx.debug.server.service;


import click.rmx.debug.RMXException;
import click.rmx.debug.server.coders.LogDecoder;
import click.rmx.debug.server.control.UpdatesEndpoint;
import click.rmx.debug.server.model.Log;
import click.rmx.debug.server.model.LogType;
import click.rmx.debug.server.repository.LogRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.websocket.DecodeException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeoutException;

import static click.rmx.debug.Bugger.print;

//import com.rabbitmq.client.*;


/**
 * Created by Max on 25/10/2015.
 */
@Service
public class LogService {
    private static final String DEBUG_EXCHANGE_NAME = "debug_topic_exchange";
    private static LogService instance;
    private Connection connection;
    private Channel channel;

    public LogService()
    {
        instance = this;
        try {
            startDebugExchange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Set<UpdatesEndpoint> endpoints = new HashSet<>();

    public static LogService getInstance() {
        return  instance;
    }

    public void addClient(UpdatesEndpoint client)
    {
        endpoints.add(client);
    }

    public void removeClient(UpdatesEndpoint client)
    {
        endpoints.remove(client);
    }

    public void notifySubscribers(Log... logs)
    {
        for (Log log : logs) {
            ObjectMapper mapper = new ObjectMapper();
            String message = "Message Failed to send! ";
            try {
                message = mapper.writeValueAsString(log);
                notiftRabbitServer(message);
            } catch (Exception e) {
                save(this.makeException(RMXException.unexpected(e)));
                e.printStackTrace();
            }

            List<UpdatesEndpoint> toRemove = new ArrayList<>();
            final String msg = message;
            endpoints.stream().forEach(e -> {
                try {
                    e.broadcast(msg);
                } catch (IOException e1) {
                    save(this.makeException(RMXException.unexpected(e1)));
                    e1.printStackTrace();
                }
            });

            toRemove.forEach(this::removeClient);
        }
    }


    private void notiftRabbitServer(String message) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(DEBUG_EXCHANGE_NAME, "topic");

        String routingKey = "updates";

        channel.basicPublish(DEBUG_EXCHANGE_NAME, routingKey, null, message.getBytes());
        System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

        connection.close();
    }



    @Resource//(type = LogRepository.class)
    private LogRepository repository;

    public boolean isActive()
    {
        return channel != null && connection != null
                && channel.isOpen() && connection.isOpen();
    }

    public boolean closeServer() throws RMXException {
        boolean chClosed = false, cnnClosed = false;
        String errors = "";
        if (this.channel != null) {
            try {
                this.channel.close();
            } catch (Exception e) {
                e.printStackTrace();
                errors += e.getMessage();
            }
            if (channel != null && !channel.isOpen()) {
                this.channel = null;
                chClosed = true;
            }
        }
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (IOException e) {
                e.printStackTrace();
                errors += "\n" + e.getMessage();
            }
            if (connection != null && !connection.isOpen()) {
                this.connection = null;
                cnnClosed = true;
            }
        }
        if (!errors.isEmpty())
            throw RMXException.unexpected("Failed to close server with exceptions: \n" + errors);
        return chClosed && cnnClosed;
    }

    public Log makeLog(byte[] body)
    {
        String message = new String(body);
        LogDecoder decoder = new LogDecoder();
        if (decoder.willDecode(message))
        {
            try {
                return decoder.decode(message);
            } catch (DecodeException e) {
                return makeException(RMXException.unexpected(e, message + "\n - Failed to decode as Log"));
            }
        }
        return makeLog(message);
    }

    public Log makeLog(HttpPost post)
    {

        HttpEntity entity = post.getEntity();
        Header[] headers = post.getAllHeaders();

        String info =
                "HEADERS" +
                "\n-------";

        for (Header h : headers) {
            info += "\n --> " + h.getName() + " == " +  String.valueOf(h.getValue());
        }

        if (entity != null) {
            info += "\nENTITY INFO for " + String.valueOf(entity) + ":";

            info += "\nLength: " + String.valueOf(entity.getContentLength());
            info += "\nType: " + String.valueOf(entity.getContentType());
            info += "\nEncoding: " + String.valueOf(entity.getContentEncoding());
            try {
                info += "\nContent: " + String.valueOf(entity.getContent());
            } catch (IOException e) {
                e.printStackTrace();
                info += "\n Exception: " + e;
            }
        } else
            info += "\nEntity was null" +
                    "\n---------------";
        info += "\n toString() == " + post.toString();
//        info += "\n getMethod() == " + post.getMethod();
//        info += "\n getConfig() == " + String.valueOf(post.getConfig());
//        info += "\n getRequestLine() == " + String.valueOf(post.getRequestLine());
//        info += "\n getProtocolVersion() == " + String.valueOf(post.getProtocolVersion());
//        info += "\n getURI() == " + String.valueOf(post.getURI());
//        try {
//            byte[] buffer = new byte[(int) entity.getContentLength()];
//            entity.getContent().read(buffer);
//            return makeLog(buffer);
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        }
//
        return makeLog(info);

    }

    private String toArrayString(Object array)
    {
        if (array.getClass().isArray() && Array.getLength(array) > 0) {
            String arrString = "{" + String.valueOf(Array.get(array, 0));
            for (int i = 1; i < Array.getLength(array); ++i)
                arrString += ", " + String.valueOf(Array.get(array, i));
            arrString += " }";
            return arrString;
        }
        return "FAIL: Not an array";
    }
    public Log makeLog(Object object)
    {
        if (object == null)
            return makeLog("NULL");
//        if (object instanceof HttpPost)
//            return makeLog((HttpPost) object);
        if (object instanceof byte[])
           return makeLog((byte[])object);
        if (object instanceof Log)
            return (Log) object;
        if (object instanceof RMXException)
            return makeException((RMXException) object);
        if (object instanceof Exception)
            return makeException(RMXException.unexpected((Exception)object));
        if (object instanceof String)
            return makeLog(String.valueOf(object));
        if (object.getClass().isArray())
            return makeLog(toArrayString(object));


        String info =
                "\nUNKNOWN OBJECT: " + object.getClass().getName() +
                "\n================" +
                        "\nMethods";
        Method[] methods = object.getClass().getMethods();
        for (Method m : methods) {
            info += "\n --(m) " + m.getReturnType().getSimpleName() + " " + m.getName() + "()";
            if (m.getReturnType() != Void.TYPE && m.getParameterCount() == 0)
                try {
                    if (m.getReturnType().isArray())
                        info += " == " + toArrayString(object);
                    else
                        info += " == " + String.valueOf(m.invoke(object));
                } catch (Exception e) {
                    info += " != FAIL: " + e;
                }
        }
        info += "\nFields:";
        Field[] fields = object.getClass().getFields();
        for (Field f: fields) {
            info += "\n --(f) " + f.getType().getSimpleName() + " " + f.getName();
        }
        info += "\nAnnotations:";
        Annotation[] annotations = object.getClass().getAnnotations();
        for (Annotation a: annotations) {
            info += "\n --(a) " + a.getClass().getSimpleName();
        }
        return makeWarning(info);
    }

    public Log makeLog(String message) {
        Log log = new Log("debug-server");
//        log.setTimeStamp(Instant.now().toEpochMilli());
        log.setMessage(message);
        log.setLogType(LogType.Message);
        return log;
    }

    public Log makeException(String message)
    {
        Log log = new Log("debug-server");
//        log.setTimeStamp(Instant.now().toEpochMilli());
        log.setMessage(message);
        log.setLogType(LogType.Exception);
        return log;
    }

    public Log makeWarning(String message)
    {
        Log log = new Log("debug-server");
//        log.setTimeStamp(Instant.now().toEpochMilli());
        log.setMessage(message);
        log.setLogType(LogType.Warning);
        return log;
    }

    public Log makeException(RMXException e) {
        return this.makeException(e.html());
    }

    public static String toHtml(String string)
    {
        return string
                .replace("\n", "<br/>")
                .replace("COMPLETED", "<span style=\"color=green;\">COMPLETED</span>")
                .replace("FAILED", "<span style=\"color=red;\">FAILED</span>");
    }


    public void startDebugExchange(String... topics) throws Exception {
        startDebugExchange(null, topics);
    }

    public void startDebugExchange(Consumer consumer, String... topics) throws IOException, TimeoutException {
        List<String> argv = Arrays.asList("debug.#", "#.log", "#.error", "#.warning", "#.exception");
        for (String s : topics)
            argv.add(s);

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
//            factory.setHost("repo.rmx.click");

//            factory.setPort(5672);
            factory.setUsername("maxbilbow");
            factory.setPassword("lskaadl");

            connection = factory.newConnection();
        }
        channel = connection.createChannel();


        channel.exchangeDeclare(DEBUG_EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();

        if (argv.size() < 1) {
            System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
            System.exit(1);
        }

        for (String bindingKey : argv) {
            channel.queueBind(queueName, DEBUG_EXCHANGE_NAME, bindingKey);
        }

        print(" [WebBugger] Waiting for messages. To exit press CTRL+C");

        if (consumer == null)
            consumer = this.defaultConsumer();
        channel.basicConsume(queueName, true, consumer);
        final LogService service = this;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (service != null) {
                    try {
                        service.closeServer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private Consumer defaultConsumer() {
        final LogService thisInstance = this;
        final ObjectMapper mapper = new ObjectMapper();
        return new DefaultConsumer(this.channel) {
            //TODO should this be transactional?
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {

                String topic = envelope.getRoutingKey().toLowerCase();
                String message = new String(body, "UTF-8");
                String log = "";
                Map map = null;
                if (message.startsWith("{") && message.endsWith("}"))
                    try {
                        map = mapper.readValue(message, new TypeReference<Map<String, String>>() {
                        });
                    } catch (JsonParseException e) {
                        print("Failed to parse as JSON: " + e.getMessage());
                    } finally {
                        if (map != null) {
                            log += "Via "+topic+":";
                            for (Object key : map.keySet()) {
                                log += "\n   --> " + key + ": " + map.get(key);
                            }
                        }
                    }
                else
                    log = message;// + " (via "+topic+")";


//                print(log);
                Log newLog = null;
                try {

                    if (topic.contains("error") || topic.contains("exception"))
                        newLog = thisInstance.makeException(log);
                    else if (topic.contains("warning"))
                        newLog = thisInstance.makeWarning(log);
                    else
                        newLog = thisInstance.makeLog(log);
                    if (properties != null)
                        newLog.setSender(properties.getAppId());

//                channel.basicAck(envelope.getDeliveryTag(),false);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (newLog != null) {
                        notifySubscribers(newLog);
                        save(newLog);
                    }
                }
            }
        };
    }

    @Transactional
    public void save(Log log)
    {
        try {
            repository.save(log);
        } catch (Exception e) {
            notifySubscribers(makeException(RMXException.unexpected(e)));
        }
    }


}
