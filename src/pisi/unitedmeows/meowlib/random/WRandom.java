package pisi.unitedmeows.meowlib.random;

import java.util.Random;

public class WRandom {


    private Random random;

    public static final WRandom BASIC = new WRandom();

    public WRandom() {
        random = new Random();
    }

    public int nextInRange(int min, int max) {
        return random.nextInt(max - min) + min;
    }   

    public byte nextByte() {
        return (byte) nextInRange(-127, 127);
    }

    public byte nextByteInRange(int min, int max) {
        return (byte) nextInRange(min, max);
    }

    public int nextInt() {
        return random.nextInt();
    }

    public int nextInt(int max) {
        return random.nextInt(max);
    }

}
