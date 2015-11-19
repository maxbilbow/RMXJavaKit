package click.rmx.debug.server.service;


import click.rmx.debug.Bugger;
import click.rmx.debug.RMXException;
import click.rmx.debug.server.LogBuilder;
import click.rmx.debug.server.coders.LogDecoder;
import click.rmx.debug.server.control.UpdatesEndpoint;
import click.rmx.debug.server.model.Log;
import click.rmx.debug.server.model.LogType;
import click.rmx.debug.server.repository.LogRepository;
import com.rabbitmq.client.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.websocket.DecodeException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

import static click.rmx.debug.Bugger.print;

//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;

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

    public LogService() {
        instance = this;
        try {
            startDebugExchange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Set<UpdatesEndpoint> endpoints = new HashSet<>();
    private Set<UpdatesEndpoint> deadEndpoints = new HashSet<>();

    public static LogService getInstance() {
        return instance;
    }

    public void addClient(UpdatesEndpoint client) {
        endpoints.add(client);
    }

    /**
     * Endpoints will be removed the next time notifySubscribers is called
     * @param client
     */
    public void removeClient(UpdatesEndpoint client) {
        deadEndpoints.add(client);
        cleanEndpoints();
    }

    private boolean notificationInProgress = false;
    private void cleanEndpoints()
    {
        if (!notificationInProgress) {
            deadEndpoints.forEach(endpoints::remove);
            deadEndpoints.clear();
        }
    }

    public void notifySubscribers(Log... logs) {
        for (Log log : logs) {
            ObjectMapper mapper = new ObjectMapper();
            String message = "Info Failed to send! ";
            try {
                message = mapper.writeValueAsString(log);
                notiftRabbitServer(message);
            } catch (Exception e) {
                save(makeException(e,"notifySubscribers(Log... logs)"));
//                e.printStackTrace();
            } finally {
                notifySubscribers(String.valueOf(message));
            }

        }

    }


    public synchronized void notifySubscribers(final String message) {
        cleanEndpoints();
        notificationInProgress = true;
                endpoints.stream().forEach(endpoint -> {
                try {
                    endpoint.broadcast(message);
                } catch (Exception e) {
//                    e.printStackTrace();
                    save(makeException(e, "notifySubscribers(final String message)"));
                }
            });
        notificationInProgress = false;
        cleanEndpoints();
    }


    private void notiftRabbitServer(String message) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(DEBUG_EXCHANGE_NAME, "topic");

        String routingKey = "updates";

        channel.basicPublish(DEBUG_EXCHANGE_NAME, routingKey, null, message.getBytes());
        System.out.println(" [RabbitMQ] Sent on topic: '" + routingKey + "', Info: '" + message + "'");

        connection.close();
    }


    @Resource//(type = LogRepository.class)
    private LogRepository repository;

    public boolean isActive() {
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

    public Log makeLog(byte[] body) {
        String message = new String(body);
        LogDecoder decoder = new LogDecoder();
        if (decoder.willDecode(message)) {
            try {
                return decoder.decode(message);
            } catch (DecodeException e) {
                return makeException(RMXException.unexpected(e, message + "\n - Failed to decode as Log"));
            }
        }
        return makeLog(message);
    }

    public Log makeLog(HttpPost post) {

        HttpEntity entity = post.getEntity();
        Header[] headers = post.getAllHeaders();

        String info =
                "HEADERS" +
                        "\n-------";

        for (Header h : headers) {
            info += "\n --> " + h.getName() + " == " + String.valueOf(h.getValue());
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

        return makeLog(info);

    }

    public Log makeLog(Object object) {
        if (object == null)
            return makeLog("NULL");
//        if (object instanceof HttpPost)
//            return makeLog((HttpPost) object);
        if (object instanceof byte[])
            return makeLog((byte[]) object);
        if (object instanceof Log)
            return (Log) object;
        if (object instanceof RMXException)
            return makeException((RMXException) object);
        if (object instanceof Exception)
            return makeException(RMXException.unexpected((Exception) object));
        if (object instanceof String)
            return makeLog(String.valueOf(object));
        if (object.getClass().isArray())
            return makeLog(Bugger.stringify(object));
//        if (Map.class.isAssignableFrom(object.getClass())) {
//            Map map = (Map) object;
//           String log = "Map:";
//                        for (Object key : map.keySet()) {
//                            log += "\n   --> " + key + ": " + map.get(key);
//                        }
//            return makeLog(log);
//        }

        return makeLog(Bugger.inspectObject(object));
    }

    public Log makeLog(String message) {
        Log log = new Log("SERVER");
//        log.setTimeStamp(Instant.now().toEpochMilli());
        log.setMessage(message);
        log.setLogType(LogType.Info);
        return log;
    }

    public Log makeException(String message) {
        Log log = new Log("SERVER");
//        log.setTimeStamp(Instant.now().toEpochMilli());
        log.setMessage(message);
        log.setLogType(LogType.Exception);
        return log;
    }

    public Log makeWarning(String message) {
        Log log = new Log("SERVER");
//        log.setTimeStamp(Instant.now().toEpochMilli());
        log.setMessage(message);
        log.setLogType(LogType.Warning);
        return log;
    }
    public Log makeException(Exception e, String message) {
        if (e instanceof RMXException)
            return makeException((RMXException) e);
        return makeException(RMXException.unexpected(e, message, 1));
    }
    public Log makeException(Exception e) {
        if (e instanceof RMXException)
            return makeException((RMXException) e);
        return makeException(RMXException.unexpected(e, 1));
    }

    public Log makeException(String message, Exception e) {
        if (e instanceof RMXException) {
            ((RMXException) e).addLog(message);
            return makeException((RMXException) e);
        }
        return makeException(RMXException.unexpected(e, message, 1));
    }

    public Log makeException(RMXException e) {
        return this.makeException(e.html());
    }

    public static String toHtml(String string) {
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

    public final LogCallback NotifyAndSave = (log, error) -> {
        if (log != null) {
            save(log);
            notifySubscribers(log);
        }
    }, Notify = (log, error) -> {
        if (log != null)
            notifySubscribers(log);
    }, Save = (log, error) -> {
        if (log != null)
            save(log);
    };

    private Consumer defaultConsumer() {
        return new DefaultConsumer(this.channel) {
            //TODO should this be transactional?
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {

                String topic = envelope.getRoutingKey().toLowerCase();
                String message = new String(body, "UTF-8");
                processMessage(message, NotifyAndSave,
                        LogBuilder.map()
                                .sender(properties.getAppId())
                                .channel(topic));
            }

            @Override
            public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                super.handleShutdownSignal(consumerTag, sig);
                if (sig != null)
                    processMessage("RabbitMQ Server shut down: " + sig, NotifyAndSave,
                        LogBuilder.map()
                                .sender("SERVER")
                                .logType(LogType.Warning));
            }
        };
    }

    public Log processMessage(String message, LogCallback callback) {
        return processMessage(message,callback,null);
    }

    public Log processMessage(String message, String sender, LogCallback callback) {
        return processMessage(message,callback,LogBuilder.map().sender(sender));
    }

    public Log processMessage(String message, LogCallback callback,  Map<String, Object> properties) {

        final String topic;
        final String sender;
        LogType logType = null;
        if (properties != null) {
            logType = (LogType) properties.getOrDefault("logType", null);
            sender = String.valueOf(properties.getOrDefault("sender", "Unknown"));
        } else {
            sender = "Unknown";
        }

        if (logType != null)
            topic = logType.channel;
        else if (properties != null)
            topic = String.valueOf(properties.getOrDefault("channel", "debug.log"));
        else
            topic = "debug.log";

        ///Try to decode as log first
        Log log = null;
        final List<Throwable> errors = new ArrayList<>();
        final LogDecoder decoder = new LogDecoder();
        try {
            log = decoder.decode(message);
        } catch (DecodeException e) {
            errors.add(e);
        }
        if (log != null) {
            if (log.getLogType() == null)
                log.setLogType(logType);
            if (log.senderIsUnknown())
                log.setSender(sender);
            if (callback != null)
                callback.invoke(log, errors);
            return log;
        }


        ///Else try to format the message
        if (logType == null)
            logType = LogType.Info;

        Map map = null;
        String logString = "";
        final ObjectMapper mapper = new ObjectMapper();
        if (message.startsWith("{") && message.endsWith("}"))
            try {
                map = mapper.readValue(message, new TypeReference<Map<String, String>>() {
                });
            } catch (Exception e) {
                errors.add(e);
            } finally {
                if (map != null) {
                    logString += "Via " + logType.channel + ":";
                    for (Object key : map.keySet()) {
                        logString += "\n   --> " + key + ": " + map.get(key);
                    }
                }
            }
        else
            logString = message;// + " (via "+topic+")";

        try {
            if (topic.contains("error") || topic.contains("exception"))
                log = this.makeException(logString);
            else if (topic.contains("warning"))
                log = this.makeWarning(logString);
            else
                log = this.makeLog(logString);
            if (properties != null) {
                log.setSender(sender == null ? "Unknown" : sender.toString());
            }
        } catch (Exception e) {
            if (log == null)
                log = makeException(e);
            errors.add(e);
        } finally {
            if (callback != null)
                callback.invoke(log, errors);
        }
        return log;
    }

    @Transactional
    public void save(Log log) {
        try {
            repository.save(log);
        } catch (Exception e) {
            notifySubscribers(makeException(e));
        }
    }

    @Transactional
    public void saveAndNotify(Log log) {
        save(log);
        notifySubscribers(log);
    }

    public String getStatusAsHtml() {
        return isActive() ?
                "<span style=\"color:green\">ON</span>" :
                "<span style=\"color:red\">OFF</span>";
    }
}
