package cn.timer.isense.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class EventManager {
    public static EventManager instance = new EventManager();

    private final ArrayList<Handle> objBus = new ArrayList<>();
    private final ArrayList<Handle> clzBus = new ArrayList<>();
    private final ArrayList<Method> registeredMethod = new ArrayList<>();

    public void register(Object... objs) {
        for (Object obj : objs) {
            for (Method method : obj.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                if (method.getParameterCount() == 1 && method.isAnnotationPresent(EventTarget.class) && !registeredMethod.contains(method)) {
                    Class<? extends Event> eventClass = (Class<? extends Event>) method.getParameterTypes()[0];
                    registeredMethod.add(method);
                    objBus.add(new Handle(obj, method, eventClass));
                }
            }
        }
    }

    public void register(Class<?>... clzs) {
        for (Class<?> clz : clzs) {
            for (Method method : clz.getDeclaredMethods()) {
                method.setAccessible(true);
                if (method.getParameterCount() == 1 && method.isAnnotationPresent(EventTarget.class) && !registeredMethod.contains(method)) {
                    Class<? extends Event> eventClass = (Class<? extends Event>) method.getParameterTypes()[0];
                    registeredMethod.add(method);
                    clzBus.add(new Handle(null, method, eventClass));
                }
            }
        }
    }

    public void unregister(Object... objs) {
        for (Object obj : objs) {
            for (int i = 0; i < this.objBus.size(); i++) {
                if (objBus.get(i).getObject().equals(obj)) {
                    registeredMethod.remove(objBus.get(i).getMethod());
                    objBus.remove(objBus.get(i));
                }
            }
        }
    }

    public void unregister(Class<?>... clzs) {
        for (Class<?> clz : clzs) {
            for (int i = 0; i < clzBus.size(); i++) {
                if (clzBus.get(i).getClass() == clz) {
                    registeredMethod.remove(clzBus.get(i).getMethod());
                    clzBus.remove(clzBus.get(i));
                }
            }
        }
    }

    private final Logger logger = LogManager.getLogger();

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void call(Event e) {
        for (int i = 0; i < objBus.size(); i++) {
            Handle bus = objBus.get(i);
            if (bus.getEventClass() == e.getClass())
                try {
                    bus.getMethod().invoke(bus.getObject(), e);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    logger.error("Failed to call the Method: " + bus.getObject().getClass().getName() + "." + bus.getMethod().getName());
                    ex.printStackTrace();
                }
        }
        for (int i = 0; i < clzBus.size(); i++) {
            Handle bus = clzBus.get(i);
            if (bus.getEventClass() == e.getClass())
                try {
                    bus.getMethod().invoke(bus.getObject(), e);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    logger.error("Failed to call the Method: " + bus.getObject().getClass().getName() + "." + bus.getMethod().getName());
                    ex.printStackTrace();
                }
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class Handle {
        private final Object object;
        private final Method method;
        private final Class<? extends Event> eventClass;

        public Handle(Object object, Method method, Class<? extends Event> eventClass) {
            this.object = object;
            this.method = method;
            this.eventClass = eventClass;
        }

        public Object getObject() {
            return object;
        }

        public Method getMethod() {
            return method;
        }

        public Class<? extends Event> getEventClass() {
            return eventClass;
        }
    }
}
