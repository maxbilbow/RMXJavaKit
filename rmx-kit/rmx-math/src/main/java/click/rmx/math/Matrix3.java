package click.rmx.math;

import javax.vecmath.Matrix3f;

/*
 * The MIT License (MIT)
 *
 * Copyright © 2015, Heiko Brumme
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


/**
 * This class represents a 3x3-Matrix. GLSL equivalent to mat3.
 *
 * @author Heiko Brumme
 */
public class Matrix3 extends Matrix3f {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7421289732835841419L;
	protected float m00, m01, m02;
    protected float m10, m11, m12;
    protected float m20, m21, m22;

    /**
     * Creates a 3x3 identity matrix.
     */
    public Matrix3() {
        setIdentity();
    }

    /**
     * Creates a 3x3 matrix with specified columns.
     *
     * @param col1 Vector with values of the first column
     * @param col2 Vector with values of the second column
     * @param col3 Vector with values of the third column
     */
    public Matrix3(Vector3 col1, Vector3 col2, Vector3 col3) {
        m00 = col1.x;
        m10 = col1.y;
        m20 = col1.z;

        m01 = col2.x;
        m11 = col2.y;
        m21 = col2.z;

        m02 = col3.x;
        m12 = col3.y;
        m22 = col3.z;
    }


    /**
     * Adds this matrix to another matrix.
     *
     * @param other The other matrix
     * @return Sum of this + other
     */
    public Matrix3 getSum(Matrix3 other) {
        Matrix3 result = new Matrix3();

        result.m00 = this.m00 + other.m00;
        result.m10 = this.m10 + other.m10;
        result.m20 = this.m20 + other.m20;

        result.m01 = this.m01 + other.m01;
        result.m11 = this.m11 + other.m11;
        result.m21 = this.m21 + other.m21;

        result.m02 = this.m02 + other.m02;
        result.m12 = this.m12 + other.m12;
        result.m22 = this.m22 + other.m22;

        return result;
    }

    /**
     * Negates this matrix.
     *
     * @return Negated matrix
     */
    public Matrix3 getNegated() {
        return getScale(-1f);
    }

    /**
     * Subtracts this matrix from another matrix.
     *
     * @param other The other matrix
     * @return Difference of this - other
     */
    public Matrix3 getSubtraction(Matrix3 other) {
        return this.getSum(other.getNegated());
    }

    /**
     * Multiplies this matrix with a scalar.
     *
     * @param scalar The scalar
     * @return Scalar product of this * scalar
     */
    public Matrix3 getScale(float scalar) {
        Matrix3 result = new Matrix3();

        result.m00 = this.m00 * scalar;
        result.m10 = this.m10 * scalar;
        result.m20 = this.m20 * scalar;

        result.m01 = this.m01 * scalar;
        result.m11 = this.m11 * scalar;
        result.m21 = this.m21 * scalar;

        result.m02 = this.m02 * scalar;
        result.m12 = this.m12 * scalar;
        result.m22 = this.m22 * scalar;

        return result;
    }

    /**
     * Multiplies this matrix to a vector.
     *
     * @param vector The vector
     * @return Vector product of this * other
     */
    public Vector3 multiply(Vector3 vector) {
        float x = this.m00 * vector.x + this.m01 * vector.y + this.m02 * vector.z;
        float y = this.m10 * vector.x + this.m11 * vector.y + this.m12 * vector.z;
        float z = this.m20 * vector.x + this.m21 * vector.y + this.m22 * vector.z;
        return new Vector3(x, y, z);
    }

    /**
     * Multiplies this matrix to another matrix.
     *
     * @param other The other matrix
     * @return Matrix product of this * other
     */
    public Matrix3 multiply(Matrix3 other) {
        Matrix3 result = new Matrix3();

        result.m00 = this.m00 * other.m00 + this.m01 * other.m10 + this.m02 * other.m20;
        result.m10 = this.m10 * other.m00 + this.m11 * other.m10 + this.m12 * other.m20;
        result.m20 = this.m20 * other.m00 + this.m21 * other.m10 + this.m22 * other.m20;

        result.m01 = this.m00 * other.m01 + this.m01 * other.m11 + this.m02 * other.m21;
        result.m11 = this.m10 * other.m01 + this.m11 * other.m11 + this.m12 * other.m21;
        result.m21 = this.m20 * other.m01 + this.m21 * other.m11 + this.m22 * other.m21;

        result.m02 = this.m00 * other.m02 + this.m01 * other.m12 + this.m02 * other.m22;
        result.m12 = this.m10 * other.m02 + this.m11 * other.m12 + this.m12 * other.m22;
        result.m22 = this.m20 * other.m02 + this.m21 * other.m12 + this.m22 * other.m22;

        return result;
    }

    /**
     * Transposes this matrix.
     *
     * @return Transposed matrix
     */
    public Matrix3 getTransposed() {
        Matrix3 result = new Matrix3();

        result.m00 = this.m00;
        result.m10 = this.m01;
        result.m20 = this.m02;

        result.m01 = this.m10;
        result.m11 = this.m11;
        result.m21 = this.m12;

        result.m02 = this.m20;
        result.m12 = this.m21;
        result.m22 = this.m22;

        return result;
    }


}

