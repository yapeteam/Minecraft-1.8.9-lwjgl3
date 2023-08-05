package pisi.unitedmeows.meowlib.reflection;

import pisi.unitedmeows.meowlib.clazz.type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
@SuppressWarnings("unchecked")
public class Reflect {

    public static Object instance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <X> X instance_c(Class<?> clazz) {
        try {
            return (X) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Field field(Class<?> clazz, String fieldName) {
        try {
            final Field field = clazz.getClass().getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field;
        } catch (NoSuchFieldException e) {

        }
        return null;
    }

    public static <X> X field(Object object, String fieldName) {
        try {
            final Field field = object.getClass().getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return (X) field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Method method(Class<?> owner, String methodName) {
        try {
            final Method method = owner.getDeclaredMethod(methodName);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }

            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method method(Class<?> owner, String methodName, Object... parameters) {
        try {
            final Method method = owner.getDeclaredMethod(methodName, type.type_array(parameters));
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }

            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <X> X call_method(Object instance, String method) {
        return call_method(instance, method, (Object) null);
    }

    public static <X> X call_method(Object instance, String method, Object... parameters) {
        try {
            Method theMethod;
            if (parameters.length == 0) {
                theMethod = instance.getClass().getDeclaredMethod(method);
            } else {
                theMethod = instance.getClass().getDeclaredMethod(method, type.type_array(parameters));
            }
            if (!theMethod.isAccessible()) {
                theMethod.setAccessible(true);
            }

            return (X) theMethod.invoke(instance, parameters);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


}
