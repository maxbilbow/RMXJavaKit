package click.rmx.engine.behaviour;

import click.rmx.core.IMessageable;
import click.rmx.engine.physics.CollisionEvent;

public interface CollisionHandler extends IBehaviour {
	void onCollisionStart(CollisionEvent event);
	void onCollisionEnd(CollisionEvent event);

//	default void broadcastMessage(String message, Object args) {
//		if (message == "onCollision")
//			this.onCollision((CollisionEvent) args);
//		else
//			super.broadcastMessage(message, args);
//	}
}
