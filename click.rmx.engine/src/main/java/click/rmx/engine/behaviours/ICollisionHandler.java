package behaviours;

import physics.CollisionEvent;

public interface ICollisionHandler {
	public void onCollisionStart(CollisionEvent event);
	public void onCollisionEnd(CollisionEvent event);
}
