package click.rmx.debug.server;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by bilbowm on 27/10/2015.
 */
@RunWith(Parameterized.class)
public class RMQLoggerTest {

    private String virtualHost;
    private String username;
    private String password;
    private final String uri;
    private Logger logger;
    private final String host;
    private final Integer port;
    private static AnObject log, warning, error;
    static int testNumber = 0;

    public RMQLoggerTest(String host, Integer port,
                      String virtualHost,
                      String username,
                      String password,
                      String  uri) {
        this.host = host;
        this.port = port;
        this.virtualHost = virtualHost;
        this.username = username;
        this.password = password;
        this.uri = uri;
    }

    @Parameterized.Parameters
    public static List<Object[]> hosts() {
        List<Object[]> hosts = new ArrayList<>();
        hosts.add(new Object[] {"localhost", 5672, null, "root", "password", null});
//        hosts.add(new Object[] {"82.37.200.76", 5672, "/", "maxbilbow", "lskaadl", null});
//        hosts.add(new Object[] {null,null,null,null,null,
//                "amqp://maxbilbow:lskaadl@82.37.200.76:5672/"});


        return hosts;

    }


    @BeforeClass
    public static void setUpBeforeClass()
    {
        log = new AnObject("LogObject #1", "THIS IS A LOG");
        warning = new AnObject("LogObject #2", "THIS IS A WARNING");
        error = new AnObject("LogObject #3", "THIS IS AN ERROR");
    }

    @Before
    public void setUp() throws Exception {
        logger = new RMQLogger(this.getClass().getCanonicalName());
        logger.setHost(host);
        logger.setPort(port);
        logger.setUri(uri);
        logger.setVirtualHost(virtualHost);
        logger.setUsername(username);
        logger.setPassword(password);
    }

    @After
    public void tearDown()
    {
        logger = null;
        try {
            Thread.sleep(100); //Optional - useful when checking subscribing clients
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }

    }

    static class AnObject {
        private final String id;
        private final String message;

        AnObject(String id, String message) {
            this.id = id;
            this.message = message;
        }

        public String getId() {
            return id;
        }

        public String getMessage() {
            return message;
        }
    }



    @Test
    public void testSendString() throws Exception {
        logger.send("This is a log", "debug.log");
        logger.send("This is a warning", "debug.warning");
        logger.send("This is a error", "debug.error");
    }

    @Test
    public void testSendObject() throws Exception {
        logger.send(log, "debug.log");
        logger.send(warning, "debug.warning");
        logger.send(error, "debug.error");
    }

    @Test
    @Ignore
    public void testSendStringWithProperties() throws Exception {

    }

    @Test
    @Ignore
    public void testSendObjectWithProperties() throws Exception {

    }
    @Test
    public void testLogWarningString() throws Exception {
        assertTrue(
                logger.logWarning("This is a warning string")
        );
    }

    @Test
    public void testLogExceptionString() throws Exception {
        assertTrue(
                logger.logException("This is a exception string")
        );
    }

    @Test
    public void testLogMessageString() throws Exception {
        assertTrue(
                logger.logMessage("This is a log string")
        );
    }

    @Test
    public void testLogWarningObject() throws Exception {
        assertTrue(
                logger.logWarning(warning)
        );
    }

    @Test
    public void testLogExceptionObject() throws Exception {
        assertTrue(
                logger.logException(error)
        );
    }

    @Test
    public void testLogMessageObject() throws Exception {
        assertTrue(
                logger.logMessage(log)
        );
    }


}