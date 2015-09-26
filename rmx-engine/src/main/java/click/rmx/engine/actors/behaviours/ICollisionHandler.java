package click.rmx.engine.actors.behaviours;

import click.rmx.engine.physics.CollisionEvent;

public interface ICollisionHandler {
	public void onCollisionStart(CollisionEvent event);
	public void onCollisionEnd(CollisionEvent event);
}
