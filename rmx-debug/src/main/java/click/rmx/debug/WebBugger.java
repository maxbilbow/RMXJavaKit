package click.rmx.debug;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.*;

import static click.rmx.debug.Bugger.print;
import static click.rmx.debug.Bugger.timestamp;

/**
 * Created by bilbowm on 13/10/2015.
 */
public class WebBugger {

    public static final String DEBUG_EXCHANGE_NAME = "debug_topic_exchange";

    private Connection connection;
    private Channel channel;
    private Consumer consumer;

    private static WebBugger instance;

    public static WebBugger getInstance()
    {
        return instance != null ? instance : (instance = new WebBugger());
    }

    private WebBugger()
    {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (instance != null) {
                    if (channel != null)
                        try {
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    if (connection != null)
                        try {
                            connection.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    consumer = null;
                }

            }
        });
    }

    private final Set<String> errors = new HashSet<>();

    private final LinkedList<String> logs = new LinkedList<>();

    public Collection<String> getErrors() {
        return errors;
    }

    public String getErrorHtml() {
            String log = "<ul>";// + this.errorLog;
            for (String error : errors) {
                log += "<li>" + error.replace("\n","<br/>") + "</li>";
            }
            return log + "</ul>";//.replace("\n", "<br/>");
    }

    public void addLog(String log) {
        this.logs.addFirst("<span style=\"color: green;\">LOG ["+timestamp()+"] >> </span>" + toHtml(log));
    }

    public void addException(String message)
    {
        this.addLog("<span style=\"color: red;\">ERR ["+timestamp()+"] >> </span>" + toHtml(message));
    }


    public void addException(Exception e, String message)
    {
        this.addException(RMXException.unexpected(e, message, 1));
    }

    public void addException(Exception e)
    {
        this.addException(RMXException.unexpected(e,1));
    }

    public void addException(RMXException e) {
        this.errors.add("<span style=\"color: red;\">ERR >> </span>" + e.html());
    }

    public static String toHtml(String string)
    {
        return string
                .replace("\n", "<br/>")
                .replace("COMPLETED", "<span style=\"color=green;\">COMPLETED</span>")
                .replace("FAILED", "<span style=\"color=red;\">FAILED</span>");
    }

    public List<String> getLogs() {
        int max = upTo(logs,10);
        return logs.subList(0,max > 0 ? max : 0);
    }

    private int upTo(LinkedList<String> logs, int i) {
        return logs.size() <= i ? logs.size() - 1 : i - 1;
    }

    public void addException(Exception e, Thread t) {
        this.addException(RMXException.unexpected(e, "FAILED WITH THREAD: " + t.getName()));
    }

    public void addFunException(String message) {
        this.addException(RMXException.newInstance(message, RMXError.JustForFun));
    }


    public void startDebugQueue(String... topics) throws IOException {
        List<String> argv = Arrays.asList("debug.#", "#.log", "#.error", "#.exception");
        for (String s : topics)
            argv.add(s);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
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

        consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                String topic = envelope.getRoutingKey().toLowerCase();
                String log = "[" + timestamp() + "] WebBugger received '" +
                        topic +
                        "':'" + message + "'";
                print(log);
                if (topic.contains("error") || topic.contains("exception"))
                    instance.addException(log);
                else
                    instance.addLog(log);

            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
