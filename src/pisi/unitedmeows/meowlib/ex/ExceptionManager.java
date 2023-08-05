package pisi.unitedmeows.meowlib.ex;

import java.util.HashMap;

public class ExceptionManager {

    private static HashMap<Thread, Ex> exceptions;

    static {
        exceptions = new HashMap<>();
    }

    public static <X extends Ex> X lastError() {
        return (X) exceptions.getOrDefault(Thread.currentThread(), null);
    }

    public static <X extends Ex> void throwEx(X ex) {
        exceptions.put(Thread.currentThread(), ex);
    }

}
