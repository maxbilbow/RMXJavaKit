package click.rmx.persistence.model;


import click.rmx.debug.Bugger;
import click.rmx.engine.Scene;
import click.rmx.engine.components.Node;
import click.rmx.engine.math.*;
import click.rmx.engine.physics.CollisionBody;
import click.rmx.engine.physics.PhysicsBody;

import click.rmx.engine.math.Vector3;

import static click.rmx.engine.math.BufferFactory.*;

public class Transform extends PersistenceTransform {

//	private Matrix4 _rMatrix = new Matrix4();
	private final BufferedMatrix4 _worldMatrix;
//	private final Matrix4 _axis;
//	private final BufferedMatrix4 _localMatrix;
	private final Quat4 _quaternion;
	private final Vector3 _left = new Vector3();
	private final Vector3 _up = new Vector3();
	private final Vector3 _fwd = new Vector3();
	private final Vector3 _localPosition = new Vector3();
	private final Vector3 _lastPosition = new Vector3();

	public Vector3 lastPosition() {
		int last = getHistory().size() - 1;
		if (last < 0) {
			_lastPosition.set(Vector3.random());
			return _lastPosition;
		}
		Matrix4Buffer mat = this.getHistory().get(last);
		_lastPosition.set(mat.x(),mat.y(),mat.z());
		return _lastPosition;
	}

	private int stepsBack = 0;
	public synchronized boolean stepBack(String args) {
		int steps = getHistory().size() - 1 - stepsBack;
		if (steps < 0)
			return false;

		if (args.contains("x"))
			this.localMatrix().m30 = getHistory().get(steps).x();
		if (args.contains("y"))
			this.localMatrix().m31 = getHistory().get(steps).y();
		if (args.contains("z"))
			this.localMatrix().m32 = getHistory().get(steps).z();
		stepsBack++;
		return true;
	}
	
	
	public synchronized void moveAlongAxis(String args, float n) {
		if (args.contains("x"))
			this.localMatrix().m30 += n;
		if (args.contains("y"))
			this.localMatrix().m31 += n;
		if (args.contains("z"))
			this.localMatrix().m32 += n;
	}
//	private EulerAngles _eulerAngles = new EulerAngles();
//	private Vector3 _scale = new Vector3(1f,1f,1f);
	public Transform(Node node) {
		super(node);
        this.setNode(node);
//		node.setTransform(this);
//		this._localMatrix = new BufferedMatrix4(MATRIX_ROW_BUFFER);
//		this._localMatrix.setIdentity();
		this._worldMatrix = new BufferedMatrix4(MATRIX_ROW_BUFFER);
		this._worldMatrix.set(localMatrix());
		
//		this._axis = new Matrix4();
//		this._axis.setIdentity();
		
//		for (int i = 0; i< history.length; ++i)
//			history[i] = new Vector3();
		_quaternion = new Quat4();
	}
	
	public Vector3 left() {
		_left.x = localMatrix().m00;
		_left.y = localMatrix().m01;
		_left.z = localMatrix().m02;
		return _left;
	}
	
	public Vector3 up() {
		_up.x = localMatrix().m10;
		_up.y = localMatrix().m11;
		_up.z = localMatrix().m12;
		return _up;
	}
	
	public Vector3 forward() {
		_fwd.x = localMatrix().m20;
		_fwd.y = localMatrix().m21;
		_fwd.z = localMatrix().m22;
		
		return _fwd;
	}


	
//	public synchronized void setScale(float x, float y, float z) {
//		_scale.x = x;
//		_scale.y = y;
//		_scale.z = z;
//	}
	
	
//	private float totalMass; private long _tmTimestamp = -1;
//
	
	
	/**
	 * TODO probably doesnt work. How do you do this maths?
	 * @return
	 */
//	private long _wmTimestamp = -1;//stops this algorithm repeating everytime called.
	public BufferedMatrix4 worldMatrix() {
//		if (node.tick() == _wmTimestamp)
//			return _worldMatrix;
		Transform parent = this.parent();
		if (parent != null && parent.parent() != null) {
			_worldMatrix.set(this.localMatrix());//.clone();
			_worldMatrix.mul(this.parent().worldMatrix());
			return _worldMatrix;
		} else {
			this._worldMatrix.set(localMatrix());
			return _worldMatrix;
		}
//		_wmTimestamp = this.node.tick();
//		return _worldMatrix;
	}
	
	public Transform parent() {
		Node parentNode = this.getNode().getParent();
		return parentNode != null ? this.getNode().getParent().transform() : null;
	}
	
	public Vector3 localPosition() {
		_localPosition.x = localMatrix().m30;
		_localPosition.y = localMatrix().m31;
		_localPosition.z = localMatrix().m32;
		return _localPosition;
	}
	

	public Vector3 position() {
		return this.worldMatrix().position();
	}
	

	public Transform rootTransform() {
		Transform parent = this.parent();
		if (parent != null && parent.parent() != null) {
			return parent.rootTransform();
		}
		return this;
	}
	
	public synchronized void translate(Vector3 v) {
		this.localMatrix().m30 += v.x;
		this.localMatrix().m31 += v.y;
		this.localMatrix().m32 += v.z;
	}
	
	public void setPosition(Vector3 position) {
		this.localMatrix().m30 = position.x;
		this.localMatrix().m31 = position.y;
		this.localMatrix().m32 = position.z;
	}
	
	public void setPosition(double d, double e, double f) {
		this.localMatrix().m30 = (float) d;
		this.localMatrix().m31 = (float) e;
		this.localMatrix().m32 = (float) f;
	}
	
	public PhysicsBody physicsBody() {
		return this.getNode().physicsBody();
	}
	
	public CollisionBody collisionBody() {
		return this.getNode().collisionBody();
	}
	
	public void move(String args) {
		String[] options = args.split(":");
		String direction = options[0];
		float scale = (float) (Float.parseFloat(options[1]) * 0.1);
		if (this.translate(direction, scale) 
				|| this.rotate(direction, scale)) 
			return;
		else
			Bugger.logAndPrint("Warning: \"" + args + "\" was not recognised", true);
		
	}
	
	public Quat4 quaternion() {
		_quaternion.set(this.worldMatrix());
		return _quaternion;
	}
	
	public Quat4 localRotation() {
		Quat4 q = new Quat4();
		q.set(localMatrix());
		return q;
	}
	
	public Vector3 eulerAngles() {
//		_rotation.set(this.worldMatrix());
		return this.worldMatrix().eulerAngles();
	}
	
	
	public EulerAngles localEulerAngles() {
		return this.localMatrix().eulerAngles();
	}
	
	
	private Matrix4 _rMatrix = new Matrix4();
	public void rotate(float radians, float x, float y, float z) {
//		Matrix4 rMatrix = new Matrix4();
		_rMatrix.setIdentity();
		_rMatrix.setRotation(new AxisAngle4(x,y,z,radians));//*  RMX.PI_OVER_180));
//		_rMatrix.transpose();
		
//		_quaternion.set(new AxisAngle4f(v.x,v.y,v.z,degrees * 0.1f));
		Vector3 p = this.localPosition();
		this.localMatrix().mul(_rMatrix);
		this.setPosition(p);
	}

	
	public boolean translate(String direction, float scale) {
		Vector3 v;
		switch (direction) {
		case "forward":
//			scale *= -1;
			v = this.forward();
			break;
		case "up":
//			scale *= -1;
			v = this.up();
			break;
		case "left":
//			scale *= -1;
			v = this.left();
			break;
		case "x":
//			scale *= -1;
			v = new Vector3(1,0,0);
			break;
		case "y":
//			scale *= -1;
			v = new Vector3(0,1,0);
			break;
		case "z":
//			scale *= -1;
			v = new Vector3(0,0,1);
			break;
		default:
			return false;
		}
		this.translate(
				v.x * scale, 
				v.y * scale,
				v.z * scale
				);
		return true;
	}
	
	
	public synchronized void translate(float x, float y, float z) {
		this.localMatrix().m30 += x;
		this.localMatrix().m31 += y;
		this.localMatrix().m32 += z;
	}
	
	public synchronized void translate(float scale, Vector3 v) {
		this.localMatrix().m30 += v.x * scale;
		this.localMatrix().m31 += v.y * scale;
		this.localMatrix().m32 += v.z * scale;
	}

	public boolean rotate(String direction, float scale) {
		Vector3 v;
		switch (direction) {
		case "pitch":
//			scale *= -1;
			v = this.left();
			break;
		case "yaw":
//			scale *= -1;
			v = this.up();
			break;
		case "roll":
//			scale *= -1;
			v = this.forward();
			break;
		case "x":
//			scale *= -1;
			v = Vector3.X;
			break;
		case "y":
//			scale *= -1;
			v = Vector3.Y;
			break;
		case "z":
//			scale *= -1;
			v = Vector3.Z;
			break;
		default:
			return false;
		}
		this.rotate(scale,v.x,v.y,v.z);
//				v.x * scale, 
//				v.y * scale,
//				v.z * scale
//				);
		return true;
	}


	public float radius() {
		return (scale().x() + scale().y() + scale().z()) / 3;
	}

	public float getWidth() {
		// TODO Auto-generated method stub
		return scale().x() * 2;
	}

	public float getHeight() {
		// TODO Auto-generated method stub
		return scale().y() * 2;
	}

	public float getLength() {
		// TODO Auto-generated method stub
		return scale().z() * 2;
	}




//	Vector3[] history = new Vector3[3];
//	private int historyCheck = 0;
//	public synchronized void updateLastPosition() {
//		for (int i=history.length-1; i>0; --i) {
//			history[i].set(history[i-1]);
//		}
//		history[0].set(this.localPosition());
//		stepsBack = 0;
//	}

}
