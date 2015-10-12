package click.rmx.engine.components;

import click.rmx.core.RMXObject;
import click.rmx.persistence.model.Transform;

public abstract class ANodeComponent extends RMXObject implements NodeComponent, UpdateableProperties {
	private boolean enabled = true;
	private boolean needsUpdate = true;
	/**
	 * Called everytime a change, t
	 */


	public ANodeComponent() {
		super();
		this.setName(this.getClass().getName());
		setNeedsUpdate();
	}

	public void updateProperties() {
		needsUpdate = false;
	}


	/* (non-Javadoc)
	 * @see click.rmx.NodeComponent#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/* (non-Javadoc)
	 * @see click.rmx.NodeComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	private Node node;
	/* (non-Javadoc)
	 * @see click.rmx.NodeComponent#setNode(click.rmx.Node)
	 */
	@Override
	public void setNode(Node node) {
		this.node = node;
		this.onAwake();
	}
	
	/**
	 * Called when node is set. Override for any aditional setup information once assigend to GameNode
	 */
	protected void onAwake(){};

	/* (non-Javadoc)
	 * @see click.rmx.NodeComponent#getNode()
	 */
	@Override
	public Node getNode() {
		return this.node;
	}
	
	/* (non-Javadoc)
	 * @see click.rmx.NodeComponent#transform()
	 */
	@Override
	public Transform transform() {
		return (Transform) this.getNode().transform();
	}
	
	/* (non-Javadoc)
	 * @see click.rmx.NodeComponent#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String forKey) {
		return this.getNode().getValue(forKey);
	}
	
	/* (non-Javadoc)
	 * @see click.rmx.NodeComponent#setValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object setValue(String forKey, Object value) {
		return this.getNode().setValue(forKey, value);
	}


	public boolean needsUpdate() {
		return needsUpdate;
	}

	public void setNeedsUpdate() {
		this.needsUpdate = true;
	}
}
