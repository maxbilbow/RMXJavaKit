package click.rmx.engine.math;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Created by Max on 26/09/2015.
 */
public class BufferedVector4 extends Vector4 implements BufferedValue<FloatBuffer> {

    private FloatBuffer buffer = BufferUtils.createFloatBuffer(4);

    public BufferedVector4(float x, float y, float z, float w) {
        super(x,y,z,w);
    }


    @Override
    public int numberOfBufferedElements() {
        return 4;
    }

    @Override
    public FloatBuffer updateBuffer() {
        buffer.clear();
        buffer.put(x).put(y).put(z).put(w);
        buffer.flip();
        return buffer;
    }
}
