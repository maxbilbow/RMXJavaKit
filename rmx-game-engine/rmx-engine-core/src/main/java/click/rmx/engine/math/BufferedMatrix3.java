package click.rmx.engine.math;

import click.rmx.math.Matrix3;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Created by Max on 26/09/2015.
 */
public class BufferedMatrix3 extends Matrix3 implements BufferedValue<FloatBuffer> {

    private FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    private int bufferSetting;

    public BufferedMatrix3(int bufferSetting) {
        super();
        this.setBufferSetting(bufferSetting);
    }

    public BufferedMatrix3() {
        super();
        this.bufferSetting = BufferFactory.MATRIX_ROW_BUFFER;
    }

    @Override
    public FloatBuffer updateBuffer() {
        switch (bufferSetting) {
            case BufferFactory.MATRIX_COL_BUFFER:
                this.colBuffer();
                return buffer;
            case BufferFactory.MATRIX_ROW_BUFFER:
                this.rowBuffer();
                return buffer;
        }
        throw new IllegalStateException("bufferSetting was not set");
    }

    public void setBufferSetting(int bufferSetting) {
        if (bufferSetting != BufferFactory.MATRIX_COL_BUFFER && bufferSetting != BufferFactory.MATRIX_ROW_BUFFER)
            throw new IllegalArgumentException("bufferSetting " + bufferSetting + "was not recognised");
        this.bufferSetting = bufferSetting;
    }

    /**
     * Returns the Buffer representation of this vector.
     *
     * @return Vector as FloatBuffer
     */
    protected FloatBuffer rowBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(9);
        buffer.put(m00).put(m01).put(m02);
        buffer.put(m10).put(m11).put(m12);
        buffer.put(m20).put(m21).put(m22);
        buffer.flip();
        return buffer;
    }

    /**
     * Returns the Buffer representation of this vector.
     *
     * @return Vector as FloatBuffer
     */
    protected FloatBuffer colBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(9);
        buffer.put(m00).put(m10).put(m20);
        buffer.put(m01).put(m11).put(m21);
        buffer.put(m02).put(m12).put(m22);
        buffer.flip();
        return buffer;
    }

    @Override
    public int numberOfBufferedElements() {
        return 9;
    }



}
