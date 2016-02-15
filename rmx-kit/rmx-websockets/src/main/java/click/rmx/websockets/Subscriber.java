package click.rmx.websockets;

/**
 * Created by Max on 13/02/2016.
 */
@FunctionalInterface
public interface Subscriber<T>
{
  void broadcast(T aMessage) throws Exception;
}
