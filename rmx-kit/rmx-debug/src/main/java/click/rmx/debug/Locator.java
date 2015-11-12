package click.rmx.debug;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by bilbowm (Max Bilbow) on 12/11/2015.
 */

public interface Locator<T> {

    String getLocation();
    Object getValue();

    abstract class AbstractLocator<T> implements Locator<T> {
        public final String location;
        public final T value;

        public AbstractLocator(String location, T result) {
            this.location = location;
            this.value = result;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public String getLocation() {
            return location;
        }
    }

    class BytesLocator extends AbstractLocator<byte[]> {

        BytesLocator(String location, byte[] result) {
            super(location, result);
        }

        @Override
        public String toString() {
            String toString;
            try {
                toString = new String(value);
            } catch (Exception e) {
                toString = "FAILED TO PARSE";
            }
            return toString;
        }
    }


    static List<BytesLocator> findBytesIn(Object object, Class<?>... ignore) {
        return findBytesIn(object, null, null, null, 5, Arrays.asList(ignore));
    }

    static List<BytesLocator> findBytesIn(Object inObject, List<BytesLocator> bytesFound, List<Object> checked, String location, int limit, List<Class<?>> ignore) {

        if (location == null)
            location = "";
        if (checked == null)
            checked = new ArrayList<>();
        if (bytesFound == null)
            bytesFound = new ArrayList<>();
        if (inObject == null || inObject.getClass().isPrimitive())
            return bytesFound;
        if (checked.contains(inObject) || ignore.contains(inObject.getClass()))
            return bytesFound;
        else
            checked.add(inObject);
        Method[] methods = inObject.getClass().getMethods();
        for (Method m : methods) {
            if (m.getParameterCount() == 0 &&
                    (!m.getReturnType().isPrimitive() || m.getReturnType() == byte[].class) &&
                    m.getReturnType() != String.class &&
//                    !m.getReturnType().getClass().isSynthetic() &&
                    !m.getName().contains("copy") &&
                    !m.getName().contains("clone") &&
                    !m.getName().contains("getClass") &&
                    !m.getName().contains("getPackage")) {
                Object ret = null;
                try {
                    ret = m.invoke(inObject);
                } catch (Exception e) {
                    System.err.println("FAILED TO INVOKE: " + e);
                    break;
                }
                final String methodName = m.getName();
                final Class<?> returnType = m.getReturnType();
                final String newLocation = location + "." + methodName + "(" + returnType.getSimpleName() + ")";

                if (returnType == byte[].class) {
                    bytesFound.add(
                            new BytesLocator(location + "." + m.getName() + "()", (byte[]) ret)
                    );
                } else if (ret == null) {
                    break;
                } else if (limit > 0 && ret.getClass().isAssignableFrom(Map.class)) {
                    Map map = (Map) ret;
                    final List<BytesLocator> bf = bytesFound;
                    final List<Object> ch = checked;
                    map.forEach((k, v) ->
                            findBytesIn(v, bf, ch, newLocation + "[" + k + "]", limit - 1, ignore)
                    );
                } else if (limit > 0 && returnType.isArray() && !inObject.getClass().isArray()) {
                    for (int i = 0; i < Array.getLength(ret); ++i)
                        findBytesIn(Array.get(ret, i), bytesFound, checked, newLocation + "[" + i + "]", limit - 1, ignore);
                } else {
                    findBytesIn(ret, bytesFound, checked, newLocation, limit, ignore);
//                    System.out.println(newLocation);
                }
            }
        }
        return bytesFound;

    }
}