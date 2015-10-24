package click.rmx.math;


import javax.vecmath.Tuple3f;
import java.io.Serializable;





public class Vector3 extends Tuple3f implements Serializable , RMXValue<Float> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -830815158587681541L;

//	public Vector3() {
//		super();
//	}
//	public Vector3(float x, float y, float z) {
//		super(x,y,z);
//	}
//
//	public Vector3(int x, int y, int z) {
//		super(x,y,z);
//	}
	
	public static final Vector3 Zero = new Vector3();
	public static final Vector3 X = new Vector3(1,0,0);
	public static final Vector3 Y = new Vector3(0,1,0);
	public static final Vector3 Z = new Vector3(0,0,1);

	public Vector3(float var1, float var2, float var3) {
		super(var1, var2, var3);
	}

	public Vector3(float[] var1) {
		super(var1);
	}



	public Vector3(Tuple3f var1) {
		super(var1);
	}


	public Vector3() {
	}

	public final float lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}

	public final float length() {
		return (float)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z));
	}

	public final void cross(Vector3 var1, Vector3 var2) {
		float var3 = var1.y * var2.z - var1.z * var2.y;
		float var4 = var2.x * var1.z - var2.z * var1.x;
		this.z = var1.x * var2.y - var1.y * var2.x;
		this.x = var3;
		this.y = var4;
	}

	public final float dot(Vector3 var1) {
		return this.x * var1.x + this.y * var1.y + this.z * var1.z;
	}

	public final void normalize(Vector3 var1) {
		float var2 = (float)(1.0D / Math.sqrt((double)(var1.x * var1.x + var1.y * var1.y + var1.z * var1.z)));
		this.x = var1.x * var2;
		this.y = var1.y * var2;
		this.z = var1.z * var2;
	}

	public final void normalize() {
		float var1 = (float)(1.0D / Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z)));
		this.x *= var1;
		this.y *= var1;
		this.z *= var1;
	}

	public final float angle(Vector3 var1) {
		double var2 = (double)(this.dot(var1) / (this.length() * var1.length()));
		if(var2 < -1.0D) {
			var2 = -1.0D;
		}

		if(var2 > 1.0D) {
			var2 = 1.0D;
		}

		return (float)Math.acos(var2);
	}

	@Override
	public Vector3 clone() {
		return (Vector3)super.clone();	
	}
	
	public boolean isZero() {
		return x == 0 && y == 0 && z == 0;
	}
	
	public float getLength() {
		float lenA = 
				x * x +
				y * y +
				z * z;
		return (float) Math.sqrt(lenA);
	}
	
	public Vector3 getVectorTo(Vector3 b) {
		float dx = b.x - x;
		float dy = b.y - y;
		float dz = b.z - z;
		return new Vector3(dx,dy,dz);
	}
	
	public float getDistanceTo(Vector3 b) {
		float dx = b.x - x;
		float dy = b.y - y;
		float dz = b.z - z;			
		return (float) Math.sqrt(
				dx * dx +
				dy * dy +
				dz * dz
				);
	}

    
    public static Vector3 makeSubtraction(Vector3 left, Vector3 right) {
    	Vector3 result = left.clone();
    	result.sub(right);
    	return result;
    }
    
    public static Vector3 makeAddition(Vector3 left, Vector3 right) {
    	Vector3 result = left.clone();
    	result.add(right);
    	return result;
    }
	public Vector3 getNormalized() {
		Vector3 n = this.clone();
		n.normalize();
		return n;
	}


    public Vector3 setAsUp(Matrix4 mat)
    {
        this.set(
                mat.m10,
                mat.m11,
                mat.m12
        );
        return this;
    }

    public Vector3 setAsLeft(Matrix4 mat)
    {
        this.set(
                mat.m00,
                mat.m01,
                mat.m02
        );
        return this;
    }

    public Vector3 setAsForward(Matrix4 mat)
    {
        this.set(
                mat.m20,
                mat.m21,
                mat.m22
        );
        return this;
    }

    public Vector3 setAsPosition(Matrix4 mat)
    {
        this.set(
                mat.m30,
                mat.m31,
                mat.m32
        );
        return this;
    }



	public Vector3 setValue(float var1, float var2, float var3) {
		this.x = var1;
		this.y = var2;
		this.z = var3;
		return this;
	}

    public static float[] random() {
        return new float[] {
                (float) Math.random() * 100,
                (float) Math.random() * 100,
                (float) Math.random() * 100,
        };
    }

	private Float [] array = new Float[3];

	public Float [] toArray()
	{
		this.array[0] = x;
		this.array[1] = y;
		this.array[2] = z;
		return this.array;
	}
}
