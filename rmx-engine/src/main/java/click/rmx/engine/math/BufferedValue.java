package click.rmx.engine.math;

import java.nio.Buffer;

/**
 * Created by Max on 26/09/2015.
 */
public interface BufferedValue<B extends Buffer> {

    int numberOfBufferedElements();

    B updateBuffer();

    default B getBuffer() {
        return updateBuffer();
    }
}
