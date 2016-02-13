package click.rmx.websockets;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by Max on 13/02/2016.
 */
public class AbstractEndpointManagerTest implements RMXEndpoint
{

  AbstractEndpointManager mEndpointManager = new AbstractEndpointManager()
  {
    @Override
    protected void onNotificationError(RMXEndpoint aRMXEndpoint, Exception e)
    {
      System.out.println(aRMXEndpoint + ": " + e);
    }
  };
  @Test
  public void notifySubscribers() throws Exception
  {
//
    mEndpointManager.subscribe(this);
    mEndpointManager.notifySubscribers("Hello!");
    mEndpointManager.unSubscribe(this);
    mEndpointManager.setStoreMessages(2);
    mEndpointManager.notifySubscribers("Hello!");
    mEndpointManager.notifySubscribers("Hello!");
    mEndpointManager.notifySubscribers("Hello!");
    mEndpointManager.notifySubscribers("Hello!");
    mEndpointManager.notifySubscribers("Hello!");
    mEndpointManager.notifySubscribers("last!");
    mEndpointManager.subscribe(this);

  }

  @Override
  public void broadcast(String message) throws IOException
  {
    System.out.println("Message Received: " + message);
  }
}