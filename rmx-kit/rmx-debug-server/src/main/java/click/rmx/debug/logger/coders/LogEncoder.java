package click.rmx.debug.logger.coders;

import click.rmx.debug.logger.model.Log;
import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by bilbowm on 05/11/2015.
 */
public class LogEncoder implements Encoder.Text<Log> {
    @Override
    public String encode(Log log) throws EncodeException {
        try {
            return new Gson().toJson(log);
        } catch (Throwable e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
