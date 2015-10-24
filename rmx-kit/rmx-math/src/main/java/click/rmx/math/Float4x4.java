package click.rmx.math;

/**
 * Created by Max on 03/10/2015.
 */
public class Float4x4 extends Float4 {

    public Float4x4(float[] tuple) {
        super(tuple);
    }

    public float m00() {
        return tuple[0];
    }

    public float m01() {
        return tuple[1];
    }
    public float m02() {
        return tuple[2];
    }

    public float m03() {
        return tuple[3];
    }
    public float m10() {
        return tuple[4];
    }
    public float m11() {
        return tuple[5];
    }
    public float m12() {
        return tuple[6];
    }
    public float m13() {
        return tuple[7];
    }
    public float m20() {
        return tuple[8];
    }
    public float m21() {
        return tuple[9];
    }
    public float m22() {
        return tuple[10];
    }
    public float m23() {
        return tuple[11];
    }
    public float m30() {
        return tuple[12];
    }
    public float m31() {
        return tuple[13];
    }
    public float m32() {
        return tuple[14];
    }
    public float m33() {
        return tuple[15];
    }

    public void setM00(float n) { tuple[0] = n; }
    public void setM01(float n) { tuple[1] = n; }
    public void setM02(float n) { tuple[2] = n; }
    public void setM03(float n) { tuple[3] = n; }

    public void setM10(float n) { tuple[4] = n; }
    public void setM11(float n) { tuple[5] = n; }
    public void setM12(float n) { tuple[6] = n; }
    public void setM13(float n) { tuple[7] = n; }

    public void setM20(float n) { tuple[8] = n; }
    public void setM21(float n) { tuple[9] = n; }
    public void setM22(float n) { tuple[10] = n; }
    public void setM23(float n) { tuple[11] = n; }

    public void setM30(float n) { tuple[12] = n; }
    public void setM31(float n) { tuple[13] = n; }
    public void setM32(float n) { tuple[14] = n; }
    public void setM33(float n) { tuple[15] = n; }


    public float x() { return tuple[12]; }
    public float y() { return tuple[13]; }
    public float z() { return tuple[14]; }
    public float w() { return tuple[15]; }

    public void setX(float n) { tuple[12] = n; }
    public void setY(float n) { tuple[13] = n; }
    public void setZ(float n) { tuple[14] = n; }
    public void setW(float n) { tuple[15] = n; }

    public float r() { return tuple[12]; }
    public float g() { return tuple[13]; }
    public float b() { return tuple[14]; }
    public float a() { return tuple[15]; }

    public void setR(float n) { tuple[12] = n; }
    public void setG(float n) { tuple[13] = n; }
    public void setB(float n) { tuple[14] = n; }
    public void setA(float n) { tuple[15] = n; }

    @Override
    public int size() {
        return 16;
    }
}
