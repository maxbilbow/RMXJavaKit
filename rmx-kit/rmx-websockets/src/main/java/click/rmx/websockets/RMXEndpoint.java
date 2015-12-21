package click.rmx.websockets;

import java.io.IOException;

/**
 * Created by Max on 20/12/2015.
 */
public interface RMXEndpoint {
    void broadcast(String message) throws IOException;
}
