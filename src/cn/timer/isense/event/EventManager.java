package cn.timer.isense.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused", "unchecked"})
public class EventManager {
    public static EventManager instance = new EventManager();
    private final ArrayList<Handler> objBus = new ArrayList<>();
    private final ArrayList<Handler> clzBus = new ArrayList<>();

    public void register(Object... objs) {
        Arrays.stream(objs)
                .filter(o -> objBus.stream().noneMatch(b -> b.getObject().equals(o)))
                .forEach(o -> {
                            Handler handler = new Handler(o, new HashMap<>());
                            Arrays.stream(o.getClass().getDeclaredMethods())
                                    .filter(m -> m.getParameterCount() == 1 && m.isAnnotationPresent(EventTarget.class))
                                    .forEach(method -> {
                                        method.setAccessible(true);
                                        Class<? extends Event> eventClass = (Class<? extends Event>) method.getParameterTypes()[0];
                                        handler.getMethods().put(eventClass, method);
                                    });
                            objBus.add(handler);
                        }
                );
    }

    public void register(Class<?>... clzs) {
        Arrays.stream(clzs)
                .filter(c -> clzBus.stream().noneMatch(b -> b.getMethods().values().toArray(new Method[0])[0].getDeclaringClass() == c))
                .forEach(c -> {
                            Handler handler = new Handler(null, new HashMap<>());
                            Arrays.stream(c.getDeclaredMethods())
                                    .filter(method -> method.getParameterCount() == 1 && method.isAnnotationPresent(EventTarget.class))
                                    .forEach(method -> {
                                        method.setAccessible(true);
                                        Class<? extends Event> eventClass = (Class<? extends Event>) method.getParameterTypes()[0];
                                        handler.getMethods().put(eventClass, method);
                                    });
                            clzBus.add(handler);
                        }
                );
    }

    public void unregister(Object... objs) {
        Arrays.stream(objs)
                .forEach(o -> objBus.stream().filter(b -> b.getObject().equals(o))
                        .findFirst().ifPresent(objBus::remove));
    }

    public void unregister(Class<?>... clzs) {
        Arrays.stream(clzs)
                .forEach(c -> clzBus.stream().filter(b -> b.getMethods().values().toArray(new Method[0])[0].getDeclaringClass() == c)
                        .findFirst().ifPresent(clzBus::remove));
    }

    private final Logger logger = LogManager.getLogger();

    public void call(Event e) {
        for (Handler bus : objBus) {
            try {
                Method method = bus.getMethods().get(e.getClass());
                if (method != null)
                    method.invoke(bus.getObject(), e);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                logger.error("Failed to call method: " + bus.getObject().getClass().getName() + "." + bus.getMethods().get(e.getClass()).getName());
                ex.printStackTrace();
            }
        }
        for (Handler bus : clzBus) {
            try {
                Method method = bus.getMethods().get(e.getClass());
                if (method != null)
                    method.invoke(bus.getObject(), e);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                logger.error("Failed to call method: " + bus.getObject().getClass().getName() + "." + bus.getMethods().get(e.getClass()).getName());
                ex.printStackTrace();
            }
        }
    }

    @Getter
    @AllArgsConstructor
    private static class Handler {
        private final Object object;
        Map<Class<? extends Event>, Method> methods;
    }
}
