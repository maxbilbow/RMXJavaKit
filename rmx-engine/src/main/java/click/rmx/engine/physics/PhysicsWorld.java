package click.rmx.engine.physics;



import click.rmx.core.RMXObject;
import click.rmx.engine.components.Node;
import click.rmx.engine.math.Vector3;
import click.rmx.persistence.model.PersistenceTransform;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

import static click.rmx.core.RMX.rmxGetCurrentFramerate;
import static click.rmx.engine.physics.CollisionBody.EXCLUSIVE_COLLISION_GROUP;
import static click.rmx.engine.physics.CollisionBody.NO_COLLISIONS;

public class PhysicsWorld extends RMXObject {
	private Vector3 gravity = new Vector3(0f,-9.8f,0f);
	private float friction = 0.3f;
	public Vector3 getGravity() {
		return gravity;
	}

	public void setGravity(Vector3 gravity) {
		this.gravity.set(gravity);
	}

	public void setGravity(float x, float y, float z) {
		this.gravity.x = x;
		this.gravity.y = y;
		this.gravity.z = z;
	}

	public void updatePhysics(Node rootNode) {
		Stream<PersistenceTransform> children = rootNode.getChildren().stream();
		
		children.forEach(transform -> {
			if (transform.getNode().physicsBody() != null) {
				this.applyGravityToNode(transform.getNode());
				transform.getNode().physicsBody().updatePhysics(this);
			}
		});
//		children.close();

	}


	private void applyGravityToNode(Node node) {
		if (this.gravity.isZero() || !node.physicsBody().isEffectedByGravity())
			return;
		float ground = node.transform().getHeight() / 2;//.scale().y / 2;
		float mass = node.transform().mass();
		float framerate = rmxGetCurrentFramerate();
		float height = node.transform().worldMatrix().m31;
		if (height > ground) {
			//			System.out.println(node.getName() + " >> BEFORE: " + m.position());
			node.physicsBody().applyForce(framerate * mass, this.gravity, Vector3.Zero);
			//			node.forces.x += g.x * framerate * mass;
			//			this.forces.y += g.y * framerate * mass;
			//			this.forces.z += g.z * framerate * mass;
		} else if (node.getParent().getParent() == null) {
			node.transform().localMatrix().m31 = ground;
		}

	}
	private Collection<CollisionBody> staticBodies = new LinkedList<>();
	private Collection<CollisionBody> dynamicBodies = new LinkedList<>();
	private Collection<CollisionBody> kinematicBodies = new LinkedList<>();
	void buildCollisionList(Collection<PersistenceTransform> collection) {
		this.staticBodies.clear();
		this.dynamicBodies.clear();
		this.kinematicBodies.clear();
		collection.forEach(transform -> {
			if (transform.getNode().collisionBody() != null) {
				switch (transform.getNode().physicsBody().getType()) {
				case Dynamic:
					this.dynamicBodies.add(transform.getNode().collisionBody());
					break;
				case Static:
					this.staticBodies.add(transform.getNode().collisionBody());
					break;
				case Kinematic:
					this.kinematicBodies.add(transform.getNode().collisionBody());
					break;
				default:
					break;
				}
			}
		});

	}

	public void updateCollisionEvents(Node rootNode) {


		this.buildCollisionList(rootNode.getChildren());


		if (!dynamicBodies.isEmpty()) {
			if (!staticBodies.isEmpty())
				checkForStaticCollisions(dynamicBodies, staticBodies);
			checkForDynamicCollisions(dynamicBodies);
			count = checks = 0;
			//			swapLists();
		} 

	}


	public void checkForStaticCollisions(Collection<CollisionBody> dynamic, Collection<CollisionBody> staticBodies) {
		Iterator<CollisionBody> si = staticBodies.iterator();
		while (si.hasNext()) {
			CollisionBody staticBody = si.next();
			Iterator<CollisionBody> di = dynamic.iterator();
			while (di.hasNext()) {		
				CollisionBody dynamicBody = di.next();
				checks++;
				if (this.checkForCollision(staticBody,dynamicBody)) {
					count++;
				}
			}
		}
	}

	int count = 0; int checks = 0;
	public void checkForDynamicCollisions(Collection<CollisionBody> dynamic) {
		CollisionBody A = ((LinkedList<CollisionBody>) dynamic).removeFirst();
		Iterator<CollisionBody> i = dynamic.iterator();
		while (i.hasNext()) {
			CollisionBody B = i.next();
			checks++;
			if (this.checkForCollision(A,B)) {
				count++;
				//					if (unchecked.remove(A))
				//						System.err.println(A.uniqueName() + " removed twie");//checked.addLast(A);
				if (!dynamic.remove(B))
					System.err.println(B.uniqueName() + " was not removed from unchecked");//checked.addLast(B);
				if (!dynamic.isEmpty()) {
					this.checkForDynamicCollisions(dynamic);
					return;
				}
			}
		}
		if (!dynamic.isEmpty()) {
			this.checkForDynamicCollisions(dynamic);
		}
	}
	Vector3 collisionDistance = new Vector3();
	public static boolean UseBoundingBox = true;
	private synchronized boolean checkForCollision(CollisionBody A, CollisionBody B) {
		if (A == B)
			return false;
		if (A.getCollisionGroup() != B.getCollisionGroup())
			if (A.getCollisionGroup() != EXCLUSIVE_COLLISION_GROUP &&
			B.getCollisionGroup() != EXCLUSIVE_COLLISION_GROUP)
				return false;

		switch (A.getCollisionGroup()) {
		case NO_COLLISIONS:
		case EXCLUSIVE_COLLISION_GROUP:
			return false;
		}

		boolean isHit = A.intersects(B);
		if (isHit) {
			CollisionEvent e = new CollisionEvent(A.getNode(),B.getNode(),securityKey);
			if (collisionDelegate != null)
				collisionDelegate.doBeforeCollision(A.getNode(), B.getNode(), e);
			e.processCollision(securityKey);
			if (collisionDelegate != null)
				collisionDelegate.doAfterCollision(A.getNode(), B.getNode(), e);

		} 
		return isHit;			

	}

	private static final int securityKey = (int) (Math.random() * 100);
	private CollisionDelegate collisionDelegate;



	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public CollisionDelegate getCollisionDelegate() {
		return collisionDelegate;
	}

	public void setCollisionDelegate(CollisionDelegate collisionDelegate) {
		this.collisionDelegate = collisionDelegate;
	}



}
