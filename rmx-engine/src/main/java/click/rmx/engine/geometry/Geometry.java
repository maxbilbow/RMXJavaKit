package click.rmx.engine.geometry;

import click.rmx.core.Categories;
import click.rmx.core.Categorizable;
import click.rmx.engine.components.NodeComponent;
import click.rmx.engine.math.Float3;
import click.rmx.engine.math.Matrix4;
import click.rmx.engine.math.Vector3;
import click.rmx.engine.math.Vector4;
import com.sun.istack.internal.NotNull;

//@Categorizable(category = Categories.GEOMETRY)
public interface Geometry extends NodeComponent, Categorizable {

	/**
	 * 
	 * @return a Shape object that must not be changed during the life of the Geometry object
	 */
	@NotNull
	Shape getShape();
	
//	void setShape(Shape shape);
	
	default Matrix4 modelMatrix() {
		return this.transform().worldMatrix();
	}
	
	default Float3 scale() {
		return this.transform().scale();
	}
	
	Vector4 getColor();
	void setColor(Vector4 color);
	
	default void setColor(int r, int g, int b, int a) {
		this.getColor().set(r, g, b, a);
	}
	
	boolean isVisible();
	
	void setVisible(boolean visible);
	
	
	
	@Deprecated
	void setShape(Shape shape);

	@Deprecated
	void render();

	@Override
	default String getCategoryName()
	{
		return Categories.GEOMETRY;
	}
	
	
//	{
//		NotificationCenter.getInstance().BroadcastMessage(GEOMETRY_WAS_DESTROYED, null);
//	}
	
}