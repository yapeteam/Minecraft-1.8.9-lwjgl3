package pisi.unitedmeows.meowlib.clazz;

import pisi.unitedmeows.meowlib.random.WRandom;

import java.util.HashMap;

public class shared<X> {
    private final HashMap<Byte, X> values = new HashMap<>();

    public X get(byte data) {
        return values.getOrDefault(data, null);
    }

    public byte put(X obj) {
        final byte identifier = WRandom.BASIC.nextByte();
        values.put(identifier, obj);
        return identifier;
    }

    public void remove(byte identifier) {
        values.remove(identifier);
    }
}
