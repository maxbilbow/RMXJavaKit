package click.rmx.engine.math;

import click.rmx.math.Vector3;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Created by Max on 26/09/2015.
 */
@Deprecated
public class BufferedVector3 extends Vector3 implements BufferedValue<FloatBuffer> {

    @Override
    public int numberOfBufferedElements() {
        return 4;
    }

    /**
     * Returns the Buffer representation of this vector.
     *
     * @return Vector as FloatBuffer
     */
    @Override
    public FloatBuffer updateBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        buffer.put(x).put(y).put(z).put(1);
        buffer.flip();
        return buffer;
    }

}
