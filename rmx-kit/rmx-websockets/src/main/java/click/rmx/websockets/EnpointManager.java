package click.rmx.websockets;

/**
 * Created by Max on 20/12/2015.
 */
public interface EnpointManager {
    void subscribe(RMXEndpoint client);

    void unSubscribe(RMXEndpoint client);

    void notifySubscribers(String message);

    long count();

    boolean hasConnections();


}
