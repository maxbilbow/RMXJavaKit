package click.rmx.engine.behaviour;

import click.rmx.engine.physics.CollisionEvent;

@Deprecated
public abstract class ACollisionHandler extends Behaviour implements CollisionHandler {

	public abstract void onCollision(final CollisionEvent e);
	public void update() {};
	
	@Override
	public void broadcastMessage(String message, Object args) {
		if (message == "onCollision")
			this.onCollision((CollisionEvent) args);
		else
			super.broadcastMessage(message, args);
	}
}
