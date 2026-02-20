package net.minecraftforge.fml.common.eventhandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private static final class Registration {
        final Object owner;
        final Method method;
        final EventPriority priority;
        final boolean receiveCanceled;

        Registration(Object owner, Method method, SubscribeEvent ann) {
            this.owner = owner;
            this.method = method;
            this.priority = ann.priority();
            this.receiveCanceled = ann.receiveCanceled();
            method.setAccessible(true);
        }
    }

    private final Map<Class<?>, CopyOnWriteArrayList<Registration>> listeners = new ConcurrentHashMap<>();

    public void register(Object target) {
        Class<?> cls = (target instanceof Class) ? (Class<?>) target : target.getClass();
        Object owner = (target instanceof Class) ? null : target;
        for (Method m : cls.getMethods()) {
            SubscribeEvent ann = m.getAnnotation(SubscribeEvent.class);
            if (ann == null) continue;
            Class<?>[] params = m.getParameterTypes();
            if (params.length != 1 || !Event.class.isAssignableFrom(params[0])) continue;
            Class<?> eventType = params[0];
            listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(new Registration(owner, m, ann));
            listeners.get(eventType).sort(Comparator.comparingInt(r -> r.priority.ordinal()));
        }
    }

    public void unregister(Object target) {
        Class<?> cls = (target instanceof Class) ? (Class<?>) target : target.getClass();
        Object owner = (target instanceof Class) ? null : target;
        for (CopyOnWriteArrayList<Registration> regs : listeners.values()) {
            regs.removeIf(r -> r.owner == owner);
        }
    }

    public boolean post(Event event) {
        List<Registration> handlers = listeners.get(event.getClass());
        if (handlers == null) return false;
        for (Registration reg : handlers) {
            if (event.isCanceled() && !reg.receiveCanceled) continue;
            try {
                reg.method.invoke(reg.owner, event);
            } catch (Exception e) {
                throw new RuntimeException("Error dispatching event " + event.getClass().getName(), e);
            }
        }
        return event.isCancelable() && event.isCanceled();
    }
}
