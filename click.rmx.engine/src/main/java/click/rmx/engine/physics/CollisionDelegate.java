package physics;

import click.rmx.engine.Node;

public interface CollisionDelegate {
	public void doAfterCollision(Node nodeA, Node nodeB, CollisionEvent eventData);
	public void doBeforeCollision(Node nodeA, Node nodeB, CollisionEvent eventData);
}
