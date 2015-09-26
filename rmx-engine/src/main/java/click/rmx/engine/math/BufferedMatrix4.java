package click.rmx.engine.math;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import static click.rmx.engine.math.BufferFactory.MATRIX_COL_BUFFER;
import static click.rmx.engine.math.BufferFactory.MATRIX_ROW_BUFFER;

/**
 * Created by Max on 26/09/2015.
 */
public class BufferedMatrix4 extends Matrix4 implements BufferedValue<FloatBuffer> {

    private FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    private int matrixBufferMode;

    public BufferedMatrix4(int matrixBufferMode) {
        super();
        this.setMatrixBufferMode(matrixBufferMode);
        ;    }

    public BufferedMatrix4(Matrix4 matrix4) {
        super();
        this.set(matrix4);
        this.matrixBufferMode = MATRIX_ROW_BUFFER;
    }

    public BufferedMatrix4() {
        super();
        this.matrixBufferMode = MATRIX_ROW_BUFFER;
    }


    //	public ByteBuffer byteBuffer() {
//		ByteBuffer buffer = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder());
//		byte[] bytes = new byte[SIZE];
//		float[] m = elements();
//		for (int i = 0; i<SIZE; ++i)
//			bytes[i] = (byte) m[i];
//		buffer.put(bytes);
//		return buffer;
//	}


    /**
     * Returns the Buffer representation of this vector.
     *
     * @return Vector as FloatBuffer
     */
    protected FloatBuffer rowBuffer() {
        buffer.clear();
        buffer.put(m00).put(m01).put(m02).put(m03);
        buffer.put(m10).put(m11).put(m12).put(m13);
        buffer.put(m20).put(m21).put(m22).put(m23);
        buffer.put(m30).put(m31).put(m32).put(m33);
        buffer.flip();
        return buffer;
    }


    /**
     * Returns the Buffer representation of this vector.
     *
     * @return Vector as FloatBuffer
     */
    protected FloatBuffer colBuffer() {
        buffer.clear();
        buffer.put(m00).put(m10).put(m20).put(m30);
        buffer.put(m01).put(m11).put(m21).put(m31);
        buffer.put(m02).put(m12).put(m22).put(m32);
        buffer.put(m03).put(m13).put(m23).put(m33);
        buffer.flip();
        return buffer;
    }

    @Override
    public int numberOfBufferedElements() {
        return 16;
    }


    @Override
    public FloatBuffer updateBuffer() {
        switch (matrixBufferMode) {
            case MATRIX_COL_BUFFER:
                this.colBuffer();
                return buffer;
            case MATRIX_ROW_BUFFER:
                this.rowBuffer();
                return buffer;
        }
        throw new IllegalStateException("matrixBufferMode was not set");
    }

    public void setMatrixBufferMode(int matrixBufferMode) {
        if (matrixBufferMode != MATRIX_COL_BUFFER && matrixBufferMode != MATRIX_ROW_BUFFER)
            throw new IllegalArgumentException("matrixBufferMode " + matrixBufferMode + "was not recognised");
        this.matrixBufferMode = matrixBufferMode;
    }
}
