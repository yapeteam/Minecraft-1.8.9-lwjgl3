package pisi.unitedmeows.meowlib.clazz;

import pisi.unitedmeows.meowlib.etc.Tuple;
import pisi.unitedmeows.meowlib.random.WRandom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class event<X extends delegate> {
    private final HashMap<Integer, Tuple<X, Method>> delegates;

    public event() {
        delegates = new HashMap<>();
    }

    public int bind(X delegate) {
        int id = WRandom.BASIC.nextInt();
        final Method method = delegate.getClass().getDeclaredMethods()[0];
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        delegates.put(id, new Tuple<>(delegate, method));
        return id;
    }

    public void unbindAll() {
        delegates.clear();
    }

    public void unbind(int id) {
        delegates.remove(id);
    }

    public void run(Object... params) {
        delegates.values().forEach(x -> {
            try {
                x.getSecond().invoke(x.getFirst(), params);
            } catch (IllegalAccessException e) {

                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

}
