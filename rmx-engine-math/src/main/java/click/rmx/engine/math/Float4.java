package click.rmx.engine.math;

/**
 * Created by Max on 03/10/2015.
 */
public class Float4 extends Float3 {

    public Float4(float[] tuple) {
        super(tuple);
    }

    public void setW(float n) {
        tuple[3] = n;
    }

    public float w() {
        return tuple[3];
    }

    @Override
    public int size() {
        return 4;
    }

    public float r() { return tuple[0]; }
    public float g() { return tuple[1]; }
    public float b() { return tuple[2]; }
    public float a() { return tuple[3]; }

    public void setR(float n) { tuple[0] = n; }
    public void setG(float n) { tuple[1] = n; }
    public void setB(float n) { tuple[2] = n; }
    public void setA(float n) { tuple[3] = n; }
}
