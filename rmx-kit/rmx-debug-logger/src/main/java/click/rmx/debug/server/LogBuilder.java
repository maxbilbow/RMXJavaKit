package click.rmx.debug.server;

import java.util.HashMap;

/**
 * Created by bilbowm on 04/11/2015.
 */
public interface LogBuilder {


    static LogBuilder map()
    {
        return new MapBuilder();
    }

    LogBuilder add(String key, Object value);
    LogBuilder add(Object object);

    class MapBuilder extends HashMap<String, Object> implements LogBuilder {

        @Override
        public LogBuilder add(String key, Object value) {
            super.put(key, value);
            return this;
        }

        @Override
        public LogBuilder add(Object object) {
            if (object != null)
              super.put(object.getClass().getSimpleName(),object);
            return null;
        }
    }
}
