package click.rmx.math;


import javax.vecmath.Matrix4f;
import javax.vecmath.Vector4f;

public class Matrix4 extends Matrix4f {
	private static int SIZE = 16;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private float[] m = new float[SIZE];

	private static final Matrix4 IDENTITY;

    public Matrix4(float[] elements) {
        super(elements);
    }

    public Matrix4() {
        super();
    }

    public static final Matrix4 Identity() {
        return IDENTITY.clone();
    }

	static {
		Matrix4 identity = new Matrix4();
		identity.setIdentity();
        IDENTITY = identity;
	}
	/**
	 * 
	 * @return float[] representation of matrix. 
	 */
	public float[] elements() {
		m[0]  = m00; m[1]  = m01; m[2]  = m02; m[3]  = m03;
		m[4]  = m10; m[5]  = m11; m[6]  = m12; m[7]  = m13;
		m[8]  = m20; m[9]  = m21; m[10] = m22; m[11] = m23;
		m[12] = m30; m[13] = m31; m[14] = m32; m[15] = m33;
		return m;
	}



    public float m00() {
        return m00;
    }

    public float m01() {
        return m01;
    }
    public float m02() {
        return m02;
    }
    public float m03() {
        return m03;
    }
    public float m10() {
        return m10;
    }
    public float m11() {
        return m11;
    }
    public float m12() {
        return m12;
    }
    public float m13() {
        return m13;
    }
    public float m20() {
        return m20;
    }
    public float m21() {
        return m21;
    }
    public float m22() {
        return m22;
    }
    public float m23() {
        return m23;
    }
    public float m30() {
        return m30;
    }
    public float m31() {
        return m31;
    }
    public float m32() {
        return m32;
    }
    public float m33() {
        return m33;
    }


	
	
	public void setPosition(Vector3 v) {
		m30 = v.x;
		m31 = v.y;
		m32 = v.z;
	}
	
	public void setPosition(int x, int y, int z) {
		m30 = x;
		m31 = y;
		m32 = z;
	}
	
	public void negatePosition() {
		m30 *= -1;
		m31 *= -1;
		m32 *= -1;
	}
	public void translate(Vector4f v) {
		m30 += v.x;
		m31 += v.y;
		m32 += v.z;
		m33 += v.w; //TODO check this is correct
	}
	
	
	
	private EulerAngles _eulerAngles = new EulerAngles();
	public EulerAngles eulerAngles() {
		_eulerAngles.y = (float) Math.atan2(-m20,m00);
		_eulerAngles.x = (float) Math.asin(m10);
		_eulerAngles.z = (float) Math.atan2(-m12,m11);
				
				
//		_eulerAngles.x = (float) Math.atan2( m22, m23);
//		_eulerAngles.y = (float) Math.atan2(-m21, Math.sqrt(m22 * m22 + m23 * m23));
//		_eulerAngles.z = (float) Math.atan2( m11, m01);
		return _eulerAngles;
	}



    /**
     * Creates a orthographic projection matrix. Similar to
     * <code>glOrtho(left, right, bottom, top, near, far)</code>.
     *
     * @param left Coordinate for the left vertical clipping pane
     * @param right Coordinate for the right vertical clipping pane
     * @param bottom Coordinate for the bottom horizontal clipping pane
     * @param top Coordinate for the bottom horizontal clipping pane
     * @param near Coordinate for the near depth clipping pane
     * @param far Coordinate for the far depth clipping pane
     * @return Orthographic matrix
     */
    public static Matrix4 orthographic(float left, float right, float bottom, float top, float near, float far) {
        Matrix4 ortho = new Matrix4();

        float tx = -(right + left) / (right - left);
        float ty = -(top + bottom) / (top - bottom);
        float tz = -(far + near) / (far - near);

        ortho.m00 = 2f / (right - left);
        ortho.m11 = 2f / (top - bottom);
        ortho.m22 = -2f / (far - near);
        ortho.m03 = tx;
        ortho.m13 = ty;
        ortho.m23 = tz;

        return ortho;
    }

    /**
     * Creates a perspective projection matrix. Similar to
     * <code>glFrustum(left, right, bottom, top, near, far)</code>.
     *
     * @param left Coordinate for the left vertical clipping pane
     * @param right Coordinate for the right vertical clipping pane
     * @param bottom Coordinate for the bottom horizontal clipping pane
     * @param top Coordinate for the bottom horizontal clipping pane
     * @param near Coordinate for the near depth clipping pane, must be positive
     * @param far Coordinate for the far depth clipping pane, must be positive
     * @return Perspective matrix
     */
    public static Matrix4f frustum(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f frustum = new Matrix4f();

        float a = (right + left) / (right - left);
        float b = (top + bottom) / (top - bottom);
        float c = -(far + near) / (far - near);
        float d = -(2f * far * near) / (far - near);

        frustum.m00 = (2f * near) / (right - left);
        frustum.m11 = (2f * near) / (top - bottom);
        frustum.m02 = a;
        frustum.m12 = b;
        frustum.m22 = c;
        frustum.m32 = -1f;
        frustum.m23 = d;
        frustum.m33 = 0f;

        return frustum;
    }

    /**
     * Creates a perspective projection matrix. Similar to
     * <code>gluPerspective(fovy, aspec, zNear, zFar)</code>.
     *
     * @param fovy Field of view angle in degrees
     * @param aspect The aspect ratio is the ratio of width to height
     * @param near Distance from the viewer to the near clipping plane, must be
     * positive
     * @param far Distance from the viewer to the far clipping plane, must be
     * positive
     * @return Perspective matrix
     */
    public static Matrix4 perspective(float fovy, float aspect, float near, float far) {
        Matrix4 perspective = new Matrix4();

        float f = (float) (1f / Math.tan(Math.toRadians(fovy) / 2f));

        perspective.m00 = f / aspect;
        perspective.m11 = f;
        perspective.m22 = (far + near) / (near - far);
        perspective.m32 = -1f;
        perspective.m23 = (2f * far * near) / (near - far);
        perspective.m33 = 0f;

        return perspective;
    }

    /**
     * Creates a translation matrix. Similar to
     * <code>glTranslate(x, y, z)</code>.
     *
     * @param x x coordinate of translation vector
     * @param y y coordinate of translation vector
     * @param z z coordinate of translation vector
     * @return Translation matrix
     */
    public static Matrix4f translate(float x, float y, float z) {
        Matrix4f translation = new Matrix4f();

        translation.m03 = x;
        translation.m13 = y;
        translation.m23 = z;

        return translation;
    }

	/**
     * Creates a rotation matrix. Similar to
     * <code>glRotate(angle, x, y, z)</code>.
     *
     * @param angle Angle of rotation in degrees
     * @param x x coordinate of the rotation vector
     * @param y y coordinate of the rotation vector
     * @param z z coordinate of the rotation vector
     * @return Rotation matrix
     */
    public static Matrix4 rotate(float angle, float x, float y, float z) {
        Matrix4 rotation = new Matrix4();

        float c = (float) Math.cos(Math.toRadians(angle));
        float s = (float) Math.sin(Math.toRadians(angle));
        Vector3 vec = new Vector3(x, y, z);
        if (vec.length() != 1f) {
            vec.normalize();
            x = vec.x;
            y = vec.y;
            z = vec.z;
        }

        rotation.m00 = x * x * (1f - c) + c;
        rotation.m10 = y * x * (1f - c) + z * s;
        rotation.m20 = x * z * (1f - c) - y * s;
        rotation.m01 = x * y * (1f - c) - z * s;
        rotation.m11 = y * y * (1f - c) + c;
        rotation.m21 = y * z * (1f - c) + x * s;
        rotation.m02 = x * z * (1f - c) + y * s;
        rotation.m12 = y * z * (1f - c) - x * s;
        rotation.m22 = z * z * (1f - c) + c;

        return rotation;
    }



    private Vector3 _position = new Vector3();
	public Vector3 position() {
		_position.x = m30;
		_position.y = m31;
		_position.z = m32;
		return _position;
	}
	
	//
	@Override//
	public Matrix4 clone() {//
		Matrix4 clone = new Matrix4();//
		clone.set(this);//
		return clone;//
	}


    public void attachBuffer(ValueBuffer buffer) {
        float [] m = buffer.getElements();
        if (m.length != this.m.length)
            throw new ArrayIndexOutOfBoundsException("Buffer length should be 16 in matrix4");
        this.m = buffer.getElements();
        this.set(m);
    }

    public void setElements(float[] elements) {
        this.m = elements;
        super.set(elements);
    }

    public static void setElements(Matrix4 mat, float[] elements) {
        mat.setElements(elements);
    }

//    public static void attacheBuffer(Matrix4 mat, ValueBuffer matrix4Buffer) {
//        mat.attachBuffer(matrix4Buffer);
//    }
}
