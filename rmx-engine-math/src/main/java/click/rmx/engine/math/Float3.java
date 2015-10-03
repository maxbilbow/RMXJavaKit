package click.rmx.engine.math;

/**
 * Created by Max on 03/10/2015.
 */
public class Float3 extends Float2 {


    public Float3(float[] tuple) {
        super(tuple);
    }

    public void setZ(float n) {
        tuple[2] = n;
    }

    public float z() {
        return tuple[2];
    }


    @Override
    public int size() {
        return 3;
    }


}