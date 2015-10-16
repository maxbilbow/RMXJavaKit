package click.rmx.engine.components;

import click.rmx.core.IMessageable;
import click.rmx.persistence.model.Transform;

public interface NodeComponent extends IMessageable, UpdateableProperties{

	boolean isEnabled();

	void setEnabled(boolean enabled);

	void setNode(Node node);

	Node getNode();

	Transform transform();



//	/**
//	 * Overriden so that multiple behaviours can share the same variables
//	 */
//	Object getValue(String forKey);
//
//	/**
//	 * Overriden so that multiple behaviours can share the same variables
//	 */
//	Object setValue(String forKey, Object value);

}