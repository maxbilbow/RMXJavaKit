package click.rmx.engine.physics;


import click.rmx.engine.behaviour.Behaviour;
import click.rmx.engine.behaviour.CollisionHandler;

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
