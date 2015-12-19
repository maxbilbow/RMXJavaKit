package click.rmx.debug.logger.coders;

import click.rmx.debug.logger.model.Log;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

/**
 * Created by bilbowm on 05/11/2015.
 */
public class LogEncoder implements Encoder.Text<Log> {
    @Override
    public String encode(Log log) throws EncodeException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(log);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
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
