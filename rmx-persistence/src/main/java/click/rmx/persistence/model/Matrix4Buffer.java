package click.rmx.persistence.model;

import click.rmx.engine.math.Matrix4;
import click.rmx.engine.math.ValueBuffer;
import click.rmx.persistence.view.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * Created by Max on 03/10/2015.
 */
@Entity(name = "buffered_matrix")
public class Matrix4Buffer implements ValueBuffer, RMXPersistence {

    @Id
    @GeneratedValue
    private Long id;

    private boolean current;

    private float[] elements;

    public Matrix4Buffer() {

    }

    public static Matrix4Buffer newInstance() {
        return new Matrix4Buffer()
                .setCurrent(true)
                .setElements(new float[]{
                        1,0,0,0,
                        0,1,0,0,
                        0,0,1,0,
                        0,0,0,1
                });
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCurrent() {
        return current;
    }

    public Matrix4Buffer setCurrent(boolean current) {
        this.current = current;
        return this;
    }

    public float[] getElements() {
        return elements;
    }

    public Matrix4Buffer setElements(float[] elements) {
        if (elements.length != 16)
            throw new ArrayIndexOutOfBoundsException("Matrix4 must have 16 elements");
        this.elements = elements;
        return this;
    }

    public Matrix4 getMatrix()
    {
        return new Matrix4(this.elements);
    }


    public static float[] Identity() {
        return new float[]{
                1,0,0,0,
                0,1,0,0,
                0,0,1,0,
                0,0,0,1
        };
    }

    public float m00() {
        return elements[0];
    }

    public float m01() {
        return elements[1];
    }
    public float m02() {
        return elements[2];
    }

    public float m03() {
        return elements[3];
    }
    public float m10() {
        return elements[4];
    }
    public float m11() {
        return elements[5];
    }
    public float m12() {
        return elements[6];
    }
    public float m13() {
        return elements[7];
    }
    public float m20() {
        return elements[8];
    }
    public float m21() {
        return elements[9];
    }
    public float m22() {
        return elements[10];
    }
    public float m23() {
        return elements[11];
    }
    public float m30() {
        return elements[12];
    }
    public float m31() {
        return elements[13];
    }
    public float m32() {
        return elements[14];
    }
    public float m33() {
        return elements[15];
    }

    public void setM00(float n) { elements[0] = n; }
    public void setM01(float n) { elements[1] = n; }
    public void setM02(float n) { elements[2] = n; }
    public void setM03(float n) { elements[3] = n; }

    public void setM10(float n) { elements[4] = n; }
    public void setM11(float n) { elements[5] = n; }
    public void setM12(float n) { elements[6] = n; }
    public void setM13(float n) { elements[7] = n; }

    public void setM20(float n) { elements[8] = n; }
    public void setM21(float n) { elements[9] = n; }
    public void setM22(float n) { elements[10] = n; }
    public void setM23(float n) { elements[11] = n; }

    public void setM30(float n) { elements[12] = n; }
    public void setM31(float n) { elements[13] = n; }
    public void setM32(float n) { elements[14] = n; }
    public void setM33(float n) { elements[15] = n; }


    public float x() { return elements[12]; }
    public float y() { return elements[13]; }
    public float z() { return elements[14]; }
    public float w() { return elements[15]; }

    public void setX(float n) { elements[12] = n; }
    public void setY(float n) { elements[13] = n; }
    public void setZ(float n) { elements[14] = n; }
    public void setW(float n) { elements[15] = n; }

    public float r() { return elements[12]; }
    public float g() { return elements[13]; }
    public float b() { return elements[14]; }
    public float a() { return elements[15]; }

    public void setR(float n) { elements[12] = n; }
    public void setG(float n) { elements[13] = n; }
    public void setB(float n) { elements[14] = n; }
    public void setA(float n) { elements[15] = n; }



}
