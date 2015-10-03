package click.rmx.engine.components;

import click.rmx.core.RMXObject;
import click.rmx.engine.Scene;
import click.rmx.engine.behaviour.IBehaviour;
import click.rmx.engine.math.Matrix4;
import click.rmx.persistence.model.PersistenceTransform;
import click.rmx.persistence.model.RMXPersistence;
import click.rmx.persistence.model.Transform;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Stream;


public class GameNode extends RMXObject implements Node, RMXPersistence {

	@Id
	@GeneratedValue
	private Long id;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
	private PersistenceTransform transform;

	@Transient
	private final HashMap<Class<?>,NodeComponent> components = new HashMap<>();

	@ManyToMany
	private final Set<IBehaviour> behaviours = new HashSet<>();
	
	/* (non-Javadoc)
	 * @see click.rmx.Node#setComponent(java.lang.Class, click.rmx.NodeComponent)
	 */
	@Override
	public void setComponent(Class<?> type, NodeComponent component) {
		this.components.put(type, component);
		component.setNode(this);
	}

	public Stream<NodeComponent> getComponents() {
		return components.values().stream();
	}

	/* (non-Javadoc)
         * @see click.rmx.Node#addBehaviour(click.rmx.IBehaviour)
         */
	@Override
	public void addBehaviour(IBehaviour behaviour) {
		if (behaviour != null) {
			for (IBehaviour b : this.behaviours) {
				if (!b.getName().isEmpty() && b.getName() == behaviour.getName()) {
					System.err.println("Behaviour: " + b.getName() + " was already added.");
					return;
				}
			}
			this.behaviours.add(behaviour);
//			behaviour.broadcastMessage("setNode",this);
			behaviour.setNode(this);
		}
	}
	
	/* (non-Javadoc)
	 * @see click.rmx.Node#getComponent(java.lang.Class)
	 */
	@Override
	public NodeComponent getComponent(Class<?> type) {
		return components.getOrDefault(type,null);
	}
	


//	private ArrayList<Node> children = new ArrayList<Node>();

//	/* (non-Javadoc)
//	 * @see click.rmx.Node#addChild(click.rmx.Node)
//	 */
//	@Override
//	public void addChild(Node child) {
//		if (!this.children.contains(child)) {
//			this.children.add(child);
//			child.setParent(this);
//		}
//	}
	
	/* (non-Javadoc)
	 * @see click.rmx.Node#removeChildNode(click.rmx.Node)
	 */
	@Override
	public boolean removeChildNode(Node node) {
		return this.getTransform().removeChild(node);
	}
	
	/* (non-Javadoc)
	 * @see click.rmx.Node#getChildWithName(java.lang.String)
	 */
	@Override
	public Node getChildWithName(String name) {
		for (PersistenceTransform child: this.getChildren()) {
			if (child.getNode().getName() == name)
				return child.getNode();
		}
		return null;
	}
	
	protected GameNode(){
		setComponent(PersistenceTransform.class, this.transform = new Transform(this));
	}

	
	/* (non-Javadoc)
	 * @see click.rmx.Node#updateLogic(long)
	 */
	@Override
	public void updateLogic(long time) {
		
		Stream<IBehaviour> behaviours = this.behaviours.stream();
		behaviours.forEach(behaviour -> {
			if (behaviour.isEnabled())
				behaviour.update(this);
		});
//		behaviours.close();		
		
		Stream<PersistenceTransform> children = this.getTransform().getChildren().stream();
		children.forEach(child -> {
			child.getNode().updateLogic(time);
		});


	}


//	private long _timeStamp = -1;
	
	/* (non-Javadoc)
	 * @see click.rmx.Node#updateAfterPhysics(long)
	 */
	@Override
	public void updateAfterPhysics(long time) {
		this.behaviours.stream().forEach(behaviour -> {
			if (behaviour.hasLateUpdate())
				behaviour.broadcastMessage("lateUpdate");
		});
		
		for (PersistenceTransform child: this.getChildren())
			child.getNode().updateAfterPhysics(time);
		transform.update();
//		this.updateTick(time);
		///.set(arg0);
//		_timeStamp = time;

	}
	
	/* (non-Javadoc)
	 * @see click.rmx.Node#draw(click.rmx.engine.Matrix4)
	 */
	@Override
	public void draw(Matrix4 viewMatrix) {
		this.geometry().updateProperties();
		if (this.geometry() != null) 
			this.geometry().render();//, modelMatrix);
		for (PersistenceTransform child: this.getChildren())
			child.getNode().draw(viewMatrix);

	}
	

	
	/* (non-Javadoc)
	 * @see click.rmx.Node#broadcastMessage(java.lang.String)
	 */
	@Override
	public void broadcastMessage(String message) {
		super.broadcastMessage(message);
//		for (NodeComponent c : this.components.values()) {
//			c.broadcastMessage(message);
//		}
		for (IBehaviour b : this.behaviours) {
			b.broadcastMessage(message);
		}
		for (PersistenceTransform child: this.getChildren()) {
			child.getNode().broadcastMessage(message);
		}
	}

	/* (non-Javadoc)
	 * @see click.rmx.Node#broadcastMessage(java.lang.String, java.lang.Object)
	 */
	@Override
	public void broadcastMessage(String message, Object args) {
		super.broadcastMessage(message, args);
		for (NodeComponent c : this.components.values()) {
			c.broadcastMessage(message, args);
		}
		for (IBehaviour b : this.behaviours) {
			b.broadcastMessage(message, args);
		}
		for (PersistenceTransform child: this.getChildren()) {
			child.getNode().broadcastMessage(message, args);
		}
	}
	
	/* (non-Javadoc)
	 * @see click.rmx.Node#sendMessageToBehaviour(java.lang.Class, java.lang.String)
	 */
	@Override
	public boolean sendMessageToBehaviour(Class<?> theBehaviour, String message) {
		return this.sendMessageToBehaviour(theBehaviour, message, null);
	}
	
	/* (non-Javadoc)
	 * @see click.rmx.Node#sendMessageToBehaviour(java.lang.Class, java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean sendMessageToBehaviour(Class<?> theBehaviour, String message, Object args) {
		for (IBehaviour b : this.behaviours) {
			if (b.getClass().equals(theBehaviour)) {
				if (args != null)
					b.broadcastMessage(message, args);
				else
					b.broadcastMessage(message);
				return true;
			}
		}
		return false;
	}
	
	

	
	/* (non-Javadoc)
	 * @see click.rmx.Node#addToCurrentScene()
	 */
	@Override
	public void addToCurrentScene() {
		this.setParent(
				Scene.getCurrent().rootNode()
		);
	}

//	private long tick = System.currentTimeMillis();
	
//	@Override
//	public long tick() {
//		// TODO Auto-generated method stub
//		return this.tick;
//	}

//	@Override
//	public void updateTick(long newTick) {
//		this.tick = newTick;
//	}

	
	public static GameNode newInstance() {
		return new GameNode();
	}
	

	public static RootNode newRootNode() {
		return RootNodeImpl.newInstance();
	}


	public PersistenceTransform getTransform() {
		return transform;
	}




	public void setTransform(PersistenceTransform transform) {
		this.setComponent(PersistenceTransform.class, this.transform = transform);
	}
}



