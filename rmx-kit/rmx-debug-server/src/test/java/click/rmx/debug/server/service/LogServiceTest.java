package click.rmx.debug.server.service;

import click.rmx.debug.RMXException;
import org.junit.*;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by bilbowm (Max Bilbow) on 12/11/2015.
 */
@Ignore
public class LogServiceTest {

    static LogService service;

    @BeforeClass
    public static void beforeClass()
    {
        service = new LogService();
    }

    @AfterClass
    public static void afterClass() throws RMXException {
        service.closeServer();
    }

    @Before
    public void setUp()
    {
        assertTrue(service.isActive());
    }

    @Test
    public void processLogTest()
    {
//        String message = "this is a log";
//        service.processMessage(message,service.Notify);
    }

}