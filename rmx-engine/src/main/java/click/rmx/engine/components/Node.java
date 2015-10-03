package click.rmx.engine.components;

import click.rmx.core.IRMXObject;

import click.rmx.engine.behaviour.IBehaviour;
import click.rmx.engine.geometry.Geometry;
import click.rmx.engine.geometry.Shape;
import click.rmx.engine.math.Matrix4;
import click.rmx.persistence.model.PersistenceTransform;
import click.rmx.persistence.model.Transform;
import click.rmx.engine.physics.CollisionBody;
import click.rmx.engine.physics.PhysicsBody;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface Node extends IRMXObject {

	void setComponent(Class<?> type, NodeComponent component);

	void addBehaviour(IBehaviour behaviour);

	NodeComponent getComponent(Class<?> type);


    Stream<NodeComponent> getComponents();

	default List<PersistenceTransform> getChildren() {
		return transform().getChildren();
	}

//	/**
//	 * @Deprecated {@See:setParent}
//	 * @param child
//	 */
//	@Deprecated
//	void addChild(Node child);

	void updateLogic(long time);

	void updateAfterPhysics(long time);

	void draw(Matrix4 viewMatrix);

	default boolean removeChildNode(Node node) {
		return transform().removeChild(node);
	}

	Node getChildWithName(String name);

	default Camera camera() {
		return (Camera) this.getComponent(Camera.class);
	}

	default void setCamera(Camera camera) {
		this.setComponent(Camera.class, camera);
	}

	
	default Geometry geometry() {
		return (Geometry) this.getComponent(Geometry.class);
	}

	default void setGeometry(Shape shape) {
		this.setComponent(Geometry.class, shape.newGeometry());
	}


	default PhysicsBody physicsBody(){
		return (PhysicsBody) this.getComponent(PhysicsBody.class);
	}
	
	default void setPhysicsBody(PhysicsBody body) {
		this.setComponent(PhysicsBody.class, body);
	}
	
	default CollisionBody collisionBody() {
		PhysicsBody body = this.physicsBody();
		if (body != null)
			return body.getCollisionBody();
		else
			return null;
	}
	

	default LightSource getLight() {
		return (LightSource) this.getComponent(LightSource.class);
	}
	
	default void setLightSource(LightSource light) {
		this.setComponent(LightSource.class, light);
	}

	default Node getParent() {
        PersistenceTransform parentTransform = getTransform().getParent();
		return  parentTransform != null ? parentTransform.getNode() : null;
	}

	default void setParent(Node parent) {
		getTransform().setParent(parent);
		setNeedsUpdate();
	}

    default void setNeedsUpdate() {
        Stream<NodeComponent> componentStream = this.getComponents();
        componentStream.forEach(component -> component.setNeedsUpdate());
    }


    default void setParent(PersistenceTransform parent) {
		getTransform().setParent(parent);
	}



	default void shine() {
		LightSource light = this.getLight();
		if (light != null)
			light.shine();
	}
	
	/**
	 * Sends a message to all behaviours and all children of this node.
	 */
	@Override
	void broadcastMessage(String message);

	/**
	 * Sends a message to all behaviours and all children of this node.
	 */
	@Override
	void broadcastMessage(String message, Object args);

	boolean sendMessageToBehaviour(Class<?> theBehaviour, String message);

	boolean sendMessageToBehaviour(Class<?> theBehaviour, String message, Object args);

	void addToCurrentScene();

	PersistenceTransform getTransform();

	default Transform transform() {
		return (Transform) getTransform();
	}

	default void addGeometryToList(Set<Geometry> geometries) {
		if (this.geometry() != null && this.geometry().isVisible())
			geometries.add(this.geometry());
		this.getChildren().stream().forEach(child -> child.getNode().addGeometryToList(geometries));
	}

//	void setTransform(Transform transform);
}