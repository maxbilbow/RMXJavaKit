package click.rmx.engine.physics;

import click.rmx.engine.components.Node;

public interface CollisionDelegate {
	public void doAfterCollision(Node nodeA, Node nodeB, CollisionEvent eventData);
	public void doBeforeCollision(Node nodeA, Node nodeB, CollisionEvent eventData);
}
