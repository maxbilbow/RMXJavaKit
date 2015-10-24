package click.rmx.engine.math;

import click.rmx.math.Matrix2;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Created by Max on 26/09/2015.
 */
public class BufferedMatrix2 extends Matrix2 implements BufferedValue<FloatBuffer> {
    private FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
    private boolean _bufferReady = false;
    private int bufferSetting;

    public BufferedMatrix2(int bufferSetting) {
        super();
        this.setBufferSetting(bufferSetting);
    }

    public BufferedMatrix2() {
        super();
        this.bufferSetting = BufferFactory.MATRIX_ROW_BUFFER;
    }

    @Override
    public FloatBuffer getBuffer() {
        updateBuffer();
        throw new IllegalStateException("bufferSetting was not set");
    }

    public void setBufferSetting(int bufferSetting) {
        if (bufferSetting != BufferFactory.MATRIX_COL_BUFFER && bufferSetting != BufferFactory.MATRIX_ROW_BUFFER)
            throw new IllegalArgumentException("bufferSetting " + bufferSetting + "was not recognised");
        this.bufferSetting = bufferSetting;
    }
    public void resetBuffers() {
        _bufferReady = false;
    }

    /**
     * Returns the Buffer representation of this vector.
     *
     * @return Vector as FloatBuffer
     */
    protected FloatBuffer rowBuffer() {
        buffer.clear();
        buffer.put(m00).put(m01);
        buffer.put(m10).put(m11);
        buffer.flip();
        return buffer;
    }

    @Override
    public int numberOfBufferedElements() {
        return 4;
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

    /**
     * Returns the Buffer representation of this vector.
     *
     * @return Vector as FloatBuffer
     */
    protected FloatBuffer colBuffer() {
        buffer.clear();
        buffer.put(m00).put(m10);
        buffer.put(m01).put(m11);
        buffer.flip();
        return buffer;
    }
}
