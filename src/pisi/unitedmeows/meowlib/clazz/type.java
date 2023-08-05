package pisi.unitedmeows.meowlib.clazz;

public class type {

    public static boolean is_type(Object object, Class<?> ... types) {
        for (Class<?> type : types) {
            if (!type.isInstance(object)) {
                return false;
            }
        }
        return true;
    }

    public static boolean is_type_or(Object object, Class<?> ... types) {
        for (Class<?> type : types) {
            if (type.isInstance(object)) {
                return true;
            }
        }
        return false;
    }

    public static <X> X cast(Object object) {
        return (X) object;
    }

    public static boolean same_type(Object object, Object... objects) {
        Class<?> type = typeof(object);
        for (Object obj : objects) {
            if (typeof(obj) != type) {
                return false;
            }
        }
        return true;
    }

    public static Class<?>[] type_array(Object... objects) {
        Class<?>[] types = new Class<?>[objects.length];
        int i = 0;
        for (Object parameter : objects) {
            types[i++] = type.typeof(parameter);
        }
        return types;
    }

    public static Class<?> typeof(Object object) {
        return object.getClass();
    }

    public static String toString(Object object) {
        return object.toString();
    }
}
