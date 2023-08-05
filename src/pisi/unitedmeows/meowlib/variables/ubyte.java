package pisi.unitedmeows.meowlib.variables;

public class ubyte extends Number implements Comparable<ubyte>, INumberOperations<ubyte> {

    // didn't expect that one did you?
    private byte value;

    public static int MAX_VALUE = Byte.MAX_VALUE * 2;
    public static int MIN_VALUE = Byte.MAX_VALUE * -2;

    public ubyte(byte val) {
        value = val;
    }

    @Override
    public void plus(ubyte value) {
        int current = intValue();
        int addValue = value.intValue();
        if (MAX_VALUE > current + addValue) {
            if (current + addValue >= Byte.MAX_VALUE) {
                this.value = convert(current + addValue);
            } else {
                this.value += addValue;
            }
        } else {
           this.value = -127;
           //todo: throw ex or something
        }
    }

    @Override
    public void minus(ubyte value) {
        int current = intValue();
        int removeValue = value.intValue();
        if (current - removeValue >= 0) {
            int newValue = current - removeValue;
            this.value = convert(newValue);
        } else {
            this.value = 0;
            //todo: throw ex or something
        }

    }


    public byte byteValue() {
        return value;
    }

    @Override
    public int compareTo(ubyte o) {
        return Integer.compare(intValue(), o.intValue());
    }

    public static byte convert(int value) {
        if (Math.abs(value) > MAX_VALUE) {
            //todo: ex
            return 0;
        }

        if (value > 127) {
            return (byte) -(value - 127);
        }
        return (byte) value;
    }

    @Override
    public int intValue() {
        int toInt;
        if (value < 0) {
            toInt = Byte.MAX_VALUE + Math.abs(value);
        } else {
            toInt = (int) value;
        }
        return toInt;
    }

    public byte raw() {
        return value;
    }

    @Override
    public long longValue() {
        return intValue();
    }

    @Override
    public float floatValue() {
        return intValue();
    }

    @Override
    public double doubleValue() {
        return intValue();
    }

    @Override
    public boolean bigger(ubyte otherVal) {
        if (raw() > 0 && otherVal.raw() > 0) {
            return raw() > otherVal.raw();
        }

        if (raw() < 0 && otherVal.raw() < 0) {
            return raw() < otherVal.raw();
        }

        if (otherVal.raw() < 0 && raw() > 0) {
            return false;
        }

        if (raw() < 0 && otherVal.raw() > 0) {
            return true;
        }

        return intValue() > otherVal.intValue();
    }

    @Override
    public boolean smaller(ubyte otherVal) {
        if (raw() > 0 && otherVal.raw() > 0) {
            return raw() < otherVal.raw();
        }

        if (raw() < 0 && otherVal.raw() < 0) {
            return raw() > otherVal.raw();
        }

        if (otherVal.raw() < 0 && raw() > 0) {
            return true;
        }

        if (raw() < 0 && otherVal.raw() > 0) {
            return false;
        }
        return intValue() < otherVal.intValue();
    }

    @Override
    public boolean same(ubyte otherVal) {
        return raw() == otherVal.raw();
    }
}
