package click.rmx.debug;

import org.junit.Test;

/**
 * Created by bilbowm (Max Bilbow) on 27/11/2015.
 */
public class LoggerTest implements Logger {

    class Logger implements click.rmx.debug.Logger {}

    Logger logger = new Logger();


    @Test
    public void testLogInfo() throws Exception {
        logInfo("Line 22: testLogInfo()");
    }

    @Test
    public void testLogWarning() throws Exception {
        logInfo("Line 27: testLogWarning()");
    }

    @Test
    public void testLogError() throws Exception {
        logError("Line 32: testLogError()");
    }

    private void printAllLevels()
    {
        logInfo(); logWarning(); logError();
    }
    @Test
    public void testLevels() throws Exception {

        System.out.println();

        logger().setDebugLevel(Bugger.DEBUG_INFO);
        System.out.println("LEVEL: INFO");
        printAllLevels();
        System.out.println();

       logger().setDebugLevel(Bugger.DEBUG_WARNING);
        System.out.println("LEVEL: WARNING");
        printAllLevels();
        System.out.println();

        logger().setDebugLevel(Bugger.DEBUG_ERROR);
        System.out.println("LEVEL: ERROR");
        printAllLevels();
        System.out.println();

        logger().setDebugLevel(Bugger.DEBUG_NONE);
        System.out.println("LEVEL: NONE");
        printAllLevels();
        System.out.println();

        logger().setDebugLevel(Bugger.DEBUG_INFO + 1);
        System.out.println("LEVEL: CUSTOM");
        printAllLevels();
        System.out.println();
    }


}