package cn.timer.isense.modules;

import cn.timer.isense.event.EventTarget;
import cn.timer.isense.event.events.EventKey;
import cn.timer.isense.modules.impl.HUD;
import cn.timer.isense.modules.impl.Notification;
import cn.timer.isense.modules.impl.Radar;
import cn.timer.isense.modules.impl.Test;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ModuleManager {
    @Getter
    private final ArrayList<AbstractModule> modules;

    public ModuleManager() {
        modules = new ArrayList<>();
    }

    public void initialize() {
        registerModule(HUD.class);
        registerModule(Radar.class);
        registerModule(Notification.class);
        registerModule(Test.class);
    }

    private static final Logger logger = LogManager.getLogger();

    private void registerModule(Class<? extends AbstractModule> module) {
        try {
            this.modules.add(module.getDeclaredConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Cannot init module: " + module.getName());
            throw new RuntimeException(e);
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getByClass(Class<T> tClass) {
        for (AbstractModule abstractModule : modules) {
            if (abstractModule.getClass() == tClass) {
                return (T) abstractModule;
            }
        }
        return null;
    }

    @EventTarget
    private void onKey(EventKey e) {
        modules.forEach(m -> {
            if (m.getKey() == e.getKey()) m.toggle();
        });
    }
}
