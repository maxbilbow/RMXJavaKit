package click.rmx.debug.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bilbowm on 04/11/2015.
 */
public interface LogBuilder extends Map<String, Object> {


    static MapBuilder map()
    {
        return new MapBuilder();
    }

    LogBuilder add(String key, Object value);
    LogBuilder add(Object object);

    default LogBuilder sender(String sender) {
        return add("sender", sender);
    }

    default LogBuilder logType(Enum logType) {
        return add("logType",logType);
    }

    default String logType() {
        return String.valueOf(get("logType"));
    }

    default LogBuilder channel(Object channel) {
        return add("channel",channel);
    }

    class MapBuilder extends HashMap<String, Object> implements LogBuilder {

        @Override
        public MapBuilder add(String key, Object value) {
            super.put(key, value);
            return this;
        }

        @Override
        public MapBuilder add(Object object) {
            if (object != null)
              super.put(object.getClass().getSimpleName(),object);
            return null;
        }
    }
}
