package click.rmx.debug;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static click.rmx.debug.Tests.note;

/**
 * Created by bilbowm (Max Bilbow) on 12/11/2015.
 */
public class BuggerTest {
    static final List<Object> list = Arrays.asList("1", 2, 3, 4, 5);
    static final Map<String, Object> map = new HashMap<>();
    static final Object[] array;
    static
    {
        map.put("one", "1");
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        map.put("five", 5);

        array = list.toArray();
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testToArrayString() throws Exception {
        note(Bugger.stringify(array));
    }

    @Test
    public void testToListString() throws Exception {
        note(Bugger.stringify(list));
    }

    @Test
    public void testToMapString() throws Exception {
        note(Bugger.stringify(map));
    }
}