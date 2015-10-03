package click.rmx.engine.math;

/**
 * Created by Max on 03/10/2015.
 */
public class Float2 extends FloatTuple {


    public Float2(float[] tuple) {
        super(tuple);
    }

    @Override
    public int size() {
        return 2;
    }

    public float x() {
        return tuple[0];
    }

    public float y() {
        return tuple[1];
    }

    public void setX(float n) {
        tuple[0] = n;
    }

    public void setY(float n) {
        tuple[1] = n;
    }
}
