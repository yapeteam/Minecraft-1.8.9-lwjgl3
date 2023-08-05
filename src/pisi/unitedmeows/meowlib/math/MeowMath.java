 package pisi.unitedmeows.meowlib.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author SIN,COS,CEIL,FLOOR,ROUND belongs to Riven (https://jvm-gaming.org/u/Riven)
 */
public class MeowMath {
    private static final float BF_SIN_TO_COS;
    private static final int BF_SIN_BITS , BF_SIN_MASK , BF_SIN_COUNT;
    private static final float BF_radFull , BF_radToIndex;
    private static final float[] BF_sinFull;
    private static final int BIG_ENOUGH_INT = 16 * 1024;
    private static final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT;
    private static final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5;
    private static final double DEG_TO_RAD = 0.0174532925199432957692369076848861271344287188854172545609719144;
    static {
        BF_SIN_TO_COS = (float) (Math.PI * 0.5f);
        BF_SIN_BITS = 12;
        BF_SIN_MASK = ~(-1 << BF_SIN_BITS);
        BF_SIN_COUNT = BF_SIN_MASK + 1;
        BF_radFull = (float) (Math.PI * 2.0);
        BF_radToIndex = BF_SIN_COUNT / BF_radFull;
        BF_sinFull = new float[BF_SIN_COUNT];
        for (int i = 0; i < BF_SIN_COUNT; i++) {
            BF_sinFull[i] = (float) Math.sin((i + Math.min(1, i % (BF_SIN_COUNT / 4)) * 0.5) / BF_SIN_COUNT * BF_radFull);
        }
    }

    /**
     * @deprecated extremely basic hypot
     */
    @Deprecated
    public static double basicHypot(double x, double y) {
        if (Double.isInfinite(x) || Double.isInfinite(y)) return Double.POSITIVE_INFINITY;
        if (Double.isNaN(x) || Double.isNaN(y)) return Double.NaN;
        return sqrt(x * x + y * y);
    }

    public static double hypot(double x, double y) {
        if (Double.isInfinite(x) || Double.isInfinite(y)) return Double.POSITIVE_INFINITY;
        if (Double.isNaN(x) || Double.isNaN(y)) return Double.NaN;
        x = Math.abs(x);
        y = Math.abs(y);
        if (x < y) {
            double d = x;
            x = y;
            y = d;
        }
        int xi = Math.getExponent(x);
        int yi = Math.getExponent(y);
        if (xi > yi + 27) return x;
        int bias = 0;
        if (xi > 510 || xi < -511) {
            bias = xi;
            x = Math.scalb(x, -bias);
            y = Math.scalb(y, -bias);
        }
        double z = 0;
        if (x > 2 * y) {
            double x1 = Double.longBitsToDouble(Double.doubleToLongBits(x) & 0xffffffff00000000L);
            double x2 = x - x1;
            z = sqrt(x1 * x1 + (y * y + x2 * (x + x1)));
        }
        else {
            double t = 2 * x;
            double t1 = Double.longBitsToDouble(Double.doubleToLongBits(t) & 0xffffffff00000000L);
            double t2 = t - t1;
            double y1 = Double.longBitsToDouble(Double.doubleToLongBits(y) & 0xffffffff00000000L);
            double y2 = y - y1;
            double x_y = x - y;
            z = sqrt(t1 * y1 + (x_y * x_y + (t1 * y2 + t2 * y)));
        }
        if (bias == 0) {
            return z;
        }
        else {
            return Math.scalb(z, bias);
        }
    }

    public static float sin(float rad) { return BF_sinFull[(int) (rad * BF_radToIndex) & BF_SIN_MASK]; }

    public static float cos(float rad) { return sin(rad + BF_SIN_TO_COS); }

    public static int floor(float x) { return (int) (x + BIG_ENOUGH_FLOOR) - BIG_ENOUGH_INT; }

    public static int round(float x) { return (int) (x + BIG_ENOUGH_ROUND) - BIG_ENOUGH_INT; }

    public static int ceil(float x) { return BIG_ENOUGH_INT - (int) (BIG_ENOUGH_FLOOR - x); }

    public static double map(double val, double mx, double from, double to) {
        return Math.min(Math.max(from + (val / mx) * (to - from), from), to);
    }

    /**
     * @implNote This multiplies the argument with 0.017453292519943295 instead of Math.PI / 180.0 which is slower.
     */
    public static double toRadians(double rad) { return rad * DEG_TO_RAD; }

    /**
     * @implNote This divides the argument with 0.017453292519943295 instead of Math.PI / 180.0 which is slower.
     */
    public static double toDegrees(double deg) { return deg / DEG_TO_RAD; }

    /**
     * @see toRadians which is faster (probably)
     */
    public static double toRadiansJava(double rad) { return rad * (Math.PI / 180.0); }

    /**
     * @see toDegrees which is faster (probably)
     */
    public static double toDegreesJava(double deg) { return deg * (180.0 / Math.PI); }

    public static double sqrt(double number) {
        double numberIn = Double.longBitsToDouble(
                ((Double.doubleToRawLongBits(number) >> 32) + (number > 100000 ? 1072679338 : 1072632448)) << 31);
        double accurateNumber1X = (numberIn + number / numberIn) * 0.5;
        double accurateNumber2X = (accurateNumber1X + number / accurateNumber1X) * 0.5;
        return accurateNumber2X;
    }

    public static double roundToPlace(double value, int places) {
        if (places < 0) {
            return value;
        }
        else {
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }

    public static int clamp(int num, int min, int max) { return Math.max(min, Math.min(max, num)); }

    public static float clamp(float num, float min, float max) { return Math.max(min, Math.min(max, num)); }

    public static double clamp(double num, double min, double max) { return Math.max(min, Math.min(max, num)); }
}
