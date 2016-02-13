package click.rmx.websockets;


//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by Max on 25/10/2015.
 */
public class AbstractEndpointManager implements EnpointManager {

  private Set<RMXEndpoint> endpoints = new HashSet<>();
  private Set<RMXEndpoint> deadEndpoints = new HashSet<>();

  @Override
  public void addClient(RMXEndpoint client) {
    endpoints.add(client);
  }

  /**
   * Endpoints will be removed the next time notifySubscribers is called
   *
   * @param client
   */
  @Override
  public void removeClient(RMXEndpoint client) {
    deadEndpoints.add(client);
    cleanEndpoints();
  }

  private boolean notificationInProgress = false;

  private void cleanEndpoints() {
    if (!notificationInProgress) {
      deadEndpoints.forEach(endpoints::remove);
      deadEndpoints.clear();
    }
  }

  @Override
  public synchronized void notifySubscribers(final String message) {
    cleanEndpoints();
    notificationInProgress = true;
    endpoints.stream().forEach(endpoint -> {
      try {
        endpoint.broadcast(message);
      } catch (Exception e) {
        onNotificationError(endpoint, e);
      }
    });
    notificationInProgress = false;
    cleanEndpoints();
  }

  protected void onNotificationError(RMXEndpoint aRMXEndpoint, Exception e) {

  }

  @Override
  public long getEndpointCount() {
    cleanEndpoints();
    return endpoints.size();
  }

  @Override
  public boolean hasConnections() {
    cleanEndpoints();
    return !endpoints.isEmpty();
  }
}






