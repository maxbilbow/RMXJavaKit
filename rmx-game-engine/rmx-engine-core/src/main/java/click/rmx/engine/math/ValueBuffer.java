package click.rmx.engine.math;

import click.rmx.math.RMXValue;

import java.nio.*;

/**
 * Created by Max on 24/10/2015.
 */
public interface ValueBuffer<V extends RMXValue,B extends Buffer> {

    B getBuffer();

    V getValue();

    ByteBuffer getByteBuffer();

    FloatBuffer getFloatBuffer();

    DoubleBuffer getDoubleBuffer();

    ShortBuffer getShortBuffer();

}
