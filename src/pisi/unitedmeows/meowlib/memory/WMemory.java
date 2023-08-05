package pisi.unitedmeows.meowlib.memory;

import java.util.HashMap;
import java.util.Random;

public class WMemory {

    private static final HashMap<Integer, Object> variables = new HashMap<>();


    public static int write(Object data) {
        int pointer = new Random().nextInt(10000); /*todo: change this */
        variables.put(pointer, data);
        return pointer;
    }

    public static void write(int pointer, Object data) {
        variables.put(pointer, data);
    }

    public void clear() {
        variables.clear();
    }

    public static void remove(int pointer) {
        variables.remove(pointer);
    }

    public static <X> X read(int pointer) {
        return (X) variables.getOrDefault(pointer, null);
    }
}
