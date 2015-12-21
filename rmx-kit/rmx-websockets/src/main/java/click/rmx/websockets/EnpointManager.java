package click.rmx.websockets;

/**
 * Created by Max on 20/12/2015.
 */
public interface EnpointManager {
    void addClient(RMXEndpoint client);

    void removeClient(RMXEndpoint client);

    void notifySubscribers(String message);

}
