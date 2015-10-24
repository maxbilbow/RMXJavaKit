package click.rmx.math;

/**
 * Created by Max on 26/09/2015.
 */

class VecMathUtil {
    static int floatToIntBits(float var0) {
        return var0 == 0.0F?0:Float.floatToIntBits(var0);
    }

    static long doubleToLongBits(double var0) {
        return var0 == 0.0D?0L:Double.doubleToLongBits(var0);
    }

    private VecMathUtil() {
    }
}
