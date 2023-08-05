package pisi.unitedmeows.meowlib.clazz;

import pisi.unitedmeows.meowlib.memory.WMemory;

public class onion<X> {
    
    private int pointer;

    public onion() {
        pointer = -1;
    }

    public onion(X value) {
        set(value);
    }

    public void set(X value) {
        if (pointer == -1) {
            pointer = WMemory.write(value);
        } else {
            WMemory.write(pointer, value);
        }
    }

    public X get() {
        if (pointer == -1) {
            return null;
        }

        return WMemory.read(pointer);
    }

    public void remove() {
        if (pointer != -1) {
            WMemory.remove(pointer);
            pointer = -1;
        }
    }
}
