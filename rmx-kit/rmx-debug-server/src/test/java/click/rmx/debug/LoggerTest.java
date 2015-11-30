package click.rmx.debug;

import org.junit.Test;

/**
 * Created by bilbowm (Max Bilbow) on 27/11/2015.
 */
public class LoggerTest implements Logger {

    @Test
    public void testLogInfo() throws Exception {
        logInfo("Line 12: testLogInfo()");
    }

    @Test
    public void testLogWarning() throws Exception {
        logInfo("Line 17: testLogWarning()");
    }

    @Test
    public void testLogError() throws Exception {
        logError("Line 22: testLogError()");
    }

    private void printAllLevels()
    {
        logInfo(); logWarning(); logError();
    }

    @Test
    public void testLevels() throws Exception {

        System.out.println();

        logger().setDebugLevel(Bugger.DEBUG_INFO);
        System.out.println("LEVEL: INFO - Expect 3 lines");
        printAllLevels();
        System.out.println();

        logger().setDebugLevel(Bugger.DEBUG_WARNING);
        System.out.println("LEVEL: WARNING - Expect 2 lines");
        printAllLevels();
        System.out.println();

        logger().setDebugLevel(Bugger.DEBUG_ERROR);
        System.out.println("LEVEL: ERROR - Expect 1 line");
        printAllLevels();
        System.out.println();

        logger().setDebugLevel(Bugger.DEBUG_NONE);
        System.out.println("LEVEL: NONE - Expect 0 lines");
        printAllLevels();
        System.out.println();

        logger().setDebugLevel(Bugger.DEBUG_INFO + 1);
        System.out.println("LEVEL: CUSTOM - Expect 3 lines");
        printAllLevels();
        System.out.println();
    }

}