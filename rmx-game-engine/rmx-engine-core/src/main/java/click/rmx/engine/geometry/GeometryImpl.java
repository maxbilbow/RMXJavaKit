package click.rmx.engine.geometry;

import click.rmx.core.NotificationCenter;
import click.rmx.core.RMX;
import click.rmx.engine.components.ANodeComponent;
import click.rmx.engine.components.Node;
import click.rmx.math.Matrix4;
import click.rmx.math.Vector3;
import click.rmx.math.Vector4;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

//@Categorizable(category = Categories.GEOMETRY)
public class GeometryImpl extends ANodeComponent implements Geometry {

	private Shape shape;
	private boolean visible = true;

	public GeometryImpl(Shape shape) {
		this.shape = shape;
	}

	public GeometryImpl() {
		this.shape = Shapes.Cube;
	}

	public boolean isVisible() {
		return this.visible;
	}
	private Matrix4 _modelView = new Matrix4();
	private Vector4 color;



	public void render() {//, Object modelMatrix) {
		
		Node node = this.getNode();
		
		_modelView.set(node.transform().worldMatrix());

		Vector3 modelA = node.transform().eulerAngles();
		//		EulerAngles modelB = base.eulerAngles();
		modelA.scale(1 / RMX.PI_OVER_180);

		glPushMatrix();

		glTranslatef(
				_modelView.m30,// + m.m30, 
				_modelView.m31,// + m.m31,
				_modelView.m32 // + m.m32
				);

		glRotatef(modelA.x, 1,0,0);
		glRotatef(modelA.y, 0,1,0);
		glRotatef(modelA.z, 0,0,1);

		float 
		X = node.transform().scale().x(),
		Y = node.transform().scale().y(),
		Z = node.transform().scale().z();
		drawWithScale(X, Y, Z);

		GL11.glPopMatrix();

	}

	protected final void drawWithScale(float X, float Y, float Z) {
		GL11.glBegin(GL11.GL_QUADS);    
		GL11.glColor3f(1.0f,1.0f,0.0f);   
		glNormal3f(0,1,0);
		GL11.glVertex3f( X, Y,-Z);        
		GL11.glVertex3f(-X, Y,-Z);        
		GL11.glVertex3f(-X, Y, Z);
		GL11.glVertex3f( X, Y, Z);  
		GL11.glColor3f(1.0f,0.5f,0.0f);  
		glNormal3f(0,-1,0);
		GL11.glVertex3f( X,-Y, Z);
		GL11.glVertex3f(-X,-Y, Z);
		GL11.glVertex3f(-X,-Y,-Z);
		GL11.glVertex3f( X,-Y,-Z);
		GL11.glColor3f(1.0f,0.0f,0.0f);
		glNormal3f(0,0,1);
		GL11.glVertex3f( X, Y, Z);
		GL11.glVertex3f(-X, Y, Z);
		GL11.glVertex3f(-X,-Y, Z);
		GL11.glVertex3f( X,-Y, Z);
		GL11.glColor3f(1.0f,1.0f,0.0f);
		glNormal3f(0,0,-1);
		GL11.glVertex3f( X,-Y,-Z);
		GL11.glVertex3f(-X,-Y,-Z);
		GL11.glVertex3f(-X, Y,-Z);
		GL11.glVertex3f( X, Y,-Z);
		GL11.glColor3f(0.0f,0.0f,1.0f);
		glNormal3f(-1,0,0);
		GL11.glVertex3f(-X, Y, Z);
		GL11.glVertex3f(-X, Y,-Z);
		GL11.glVertex3f(-X,-Y,-Z);
		GL11.glVertex3f(-X,-Y, Z);
		GL11.glColor3f(1.0f,0.0f,1.0f);
		glNormal3f(1,0,0);
		GL11.glVertex3f( X, Y,-Z);
		GL11.glVertex3f( X, Y, Z);
		GL11.glVertex3f( X,-Y, Z);
		GL11.glVertex3f( X,-Y,-Z);
		GL11.glEnd();    

	}
	
	public static GeometryImpl cube() {
		return new GeometryImpl();
	}


	public Shape getShape() {
		return this.shape;
	}

	public void setShape(Shape shape) {	
		this.shape = shape;
	}


	public Vector4 getColor() {
		return this.color;
	}


	public void setColor(Vector4 color) {
		this.color = color;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public String getCategoryName() {
		return Geometry.super.getCategoryName();
	}
	
	public static final String GEOMETRY_WAS_DESTROYED = "GEOMETRY_WAS_DESTROYED";
	public void finalize() throws Throwable {
		NotificationCenter.getInstance().BroadcastMessage(GEOMETRY_WAS_DESTROYED, null);
		super.finalize();
	}
	

	
}
