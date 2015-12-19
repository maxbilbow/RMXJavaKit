package click.rmx.debug.logger.coders;

import click.rmx.debug.logger.model.Log;
import click.rmx.debug.logger.model.LogType;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

/**
 * Created by bilbowm on 05/11/2015.
 */
public class LogDecoder implements Decoder.Text<Log> {

    static final String
    MESSAGE = "Message", INFO = "Info", EXCEPTION = "Exception", WARNING = "Warning";

    @Override
    public Log decode(String msg) throws DecodeException {
        Log log = new Log();

        if(willDecode(msg)){
            try {
                JsonObject obj = Json.createReader(new StringReader(msg)).readObject();
//                ObjectMapper mapper = new ObjectMapper();
                String type = obj.getString("logType");
                switch (type) {
                    case MESSAGE:
                        log.setLogType(LogType.Message);
                        break;
                    case INFO:
                        log.setLogType(LogType.Info);
                        break;
                    case WARNING:
                        log.setLogType(LogType.Warning);
                        break;
                    case EXCEPTION:
                        log.setLogType(LogType.Exception);
                        break;
                    default:
                        log.setLogType(null);
                        break;
                }
                log.setMessage(obj.getString(msg));
                log.setChannel(log.getLogType().channel);
                log.setSender(obj.getString("sender"));
            } catch(Exception e){
                e.printStackTrace();
                log.setMessage(msg);
            }
        } else
            log.setMessage(msg);
        return log;
    }

    @Override
    public boolean willDecode(String msg) {
        try {
            Json.createReader((new StringReader(msg)));
            return true;
        } catch (JsonException e){
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
