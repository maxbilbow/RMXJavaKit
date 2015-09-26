package click.rmx.engine.math;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Created by Max on 26/09/2015.
 */
public class BufferedVector2 extends Vector2 implements BufferedValue<FloatBuffer> {
//    private FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(2);
    private ByteBuffer buffer = BufferUtils.createByteBuffer(2*4);

    @Override
    public int numberOfBufferedElements() {
        return 2;
    }


    /**
     * Returns the Buffer representation of this vector.
     *
     * @return Vector as FloatBuffer
     */
    @Override
    public FloatBuffer updateBuffer() {
        buffer.clear();
        buffer.putFloat(x).putFloat(y);
        buffer.flip();
        return buffer.asFloatBuffer();
    }
}
