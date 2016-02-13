package click.rmx.websockets;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * Created by Max on 25/10/2015.
 */
public abstract class AbstractEndpointManager implements EnpointManager
{

  private Set<RMXEndpoint> mSubscribers = new HashSet<>();
  private Set<RMXEndpoint> deadEndpoints = new HashSet<>();

  private LinkedBlockingDeque<String> mMessageQueue = new LinkedBlockingDeque<>();
  private boolean mQueueActive = false;

  private int mStoreMessages = 1;

  @Override
  public void subscribe(RMXEndpoint client)
  {
    mSubscribers.add(client);
    if (!mQueueActive && !mMessageQueue.isEmpty())
      sendMessageQueue();
  }

  /**
   * Endpoints will be removed the next time notifySubscribers is called
   *
   * @param client
   */
  @Override
  public void unSubscribe(RMXEndpoint client)
  {
    deadEndpoints.add(client);
    cleanEndpoints();
  }


  private void cleanEndpoints()
  {
    if (!mQueueActive && !deadEndpoints.isEmpty())
    {
      deadEndpoints.forEach(mSubscribers::remove);
      deadEndpoints.clear();
    }
  }


  protected void sendMessageQueue()
  {
    if (mQueueActive)
      return;

    if (hasConnections())
    {
      mQueueActive = true;

      while (!mMessageQueue.isEmpty())
      {
        try
        {
          final String message = mMessageQueue.take();
          mSubscribers.stream().forEach(endpoint -> {
            try
            {
              endpoint.broadcast(message);
            } catch (Exception e)
            {
              onNotificationError(endpoint, e);
            }
          });
        } catch (Exception e)
        {
          onNotificationError(null, e);
        }
      }

      mQueueActive = false;
    }

  }

  @Override
  public void notifySubscribers(final String message)
  {
    if (isStoreMessages() || hasConnections())
    {
      mMessageQueue.add(message);
      if (mMessageQueue.size() > mStoreMessages)
      {
        mMessageQueue.removeFirst();
      }
      sendMessageQueue();
    }
  }

  protected abstract void onNotificationError(RMXEndpoint aRMXEndpoint, Exception e);


  @Override
  public long count()
  {
    cleanEndpoints();
    return mSubscribers.size();
  }

  @Override
  public boolean hasConnections()
  {
    cleanEndpoints();
    return !mSubscribers.isEmpty();
  }

  public boolean isStoreMessages()
  {
    return mStoreMessages > 0;
  }

  public void setStoreMessages(int aStoreMessages)
  {
    mStoreMessages = aStoreMessages;
  }
}






