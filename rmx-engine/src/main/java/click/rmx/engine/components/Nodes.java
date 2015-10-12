package click.rmx.engine.components;

import click.rmx.engine.GameController;
import click.rmx.engine.Scene;

import click.rmx.engine.behaviour.CameraBehaviour;
import click.rmx.engine.behaviour.IBehaviour;
import click.rmx.engine.behaviour.SpriteBehaviour;
import click.rmx.engine.geometry.Shapes;
import click.rmx.engine.math.Tools;
import click.rmx.engine.physics.PhysicsBody;
import click.rmx.persistence.model.PersistenceTransform;

import java.util.Collection;
import java.util.stream.Stream;

import static click.rmx.engine.behaviour.Ai.*;


public final class Nodes {
//	public static Node newWeakNode() {
//		return WeakNode.newInstnance();
//	}
//	
//	public static Node newWeakNode(String name) {
//		WeakNode node = WeakNode.newInstnance();
//		node.setName(name);
//		return node;
//	}
	
	public static Node newGameNode() {
		return GameNode.newInstance();
	}


	public static Node defaultAiNode()
	{
		Node body = Nodes.makeCube(1, PhysicsBody.newDynamicBody(), new SpriteBehaviour());

		//				body.addBehaviour(new SpriteBehaviour());
//		body.physicsBody().setMass(1);
		//				body.physicsBody().setRestitution((float)Tools.rBounds(2,8)/10);
		//				body.transform().setScale(2, 2, 2);
		//				body.physicsBody().setDamping(0f);
		//				body.physicsBody().setFriction(0f);
		//				body.physicsBody().setRestitution(0);
		//				body.setCollisionBody(new CollisionBody());
		Node head = Nodes.makeCube(body.transform().radius() / 2, null, null);
		head.setName("head");

		head.setParent(body);
		head.transform().setPosition(0, //put head where a head should be
				body.transform().scale().y() + head.transform().scale().y(),
				body.transform().scale().z() + head.transform().scale().z());
		Node trailingCam = Nodes.newCameraNode();
		trailingCam.setParent(head);
		trailingCam.setName("trailingCam");
		trailingCam.transform().translate(0, 20, 50);

		return body;
	}
	public static Node newGameNode(String name) {
		GameNode node = GameNode.newInstance();
		node.setName(name);
		return node;
	}
	
	public static RootNode newRootNode() {
		return GameNode.newRootNode();
	}
	
	private static Node current;
	public static void setCurrent(Node n) {
		current = n;
	}
	
	public static Node getCurrent() {
		if (current == null)
			current = Nodes.newGameNode("Player");
		return current;
	}
	
	public static Node newCameraNode() {
		Node cameraNode = Nodes.newGameNode("CameraNode");
		cameraNode.setCamera(new Camera());
		cameraNode.addBehaviour(new CameraBehaviour());
		return cameraNode;
	}
	
	public static Node makeCube(float s, PhysicsBody body, IBehaviour b) {
		Node n = Nodes.newGameNode("Cube");
		n.setGeometry(Shapes.Cube);
		if (body != null)
			n.setPhysicsBody(body);
		n.transform().setScale(s, s, s);
		n.addBehaviour(b);
//		n.addToCurrentScene();
		return n;
	}
	
	public static Node randomAiNode() {
		Stream stream;
		

			Collection<PersistenceTransform> nodes =  Scene.getCurrent().rootNode().getChildren();//.clone();

			stream = nodes.stream().filter(transform -> {
				return transform.getNode().getValue(GET_AI_STATE) == null;
			});
		
//		nodes.stream()
//		nodes.removeIf(new Predicate<Node>() {
//
//			@Override
//			public boolean test(Node t) {
//				
//				return t.getValue(Behaviour.GET_AI_STATE) == null;
//			}
//			
//		});
		Object[] arr = stream.toArray();
		return (Node) arr[(int) Tools.rBounds(0, arr.length)];
	}
	
	
}
