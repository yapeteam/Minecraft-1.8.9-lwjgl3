import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;

import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Logger;

public class ModDiscovery {
    private static final Logger LOGGER = Logger.getLogger("ModDiscovery");

    public static void discover(URLClassLoader classLoader) {
        for (URL url : classLoader.getURLs()) {
            try {
                java.io.File file = new java.io.File(url.toURI());
                if (!file.exists() || !file.getName().endsWith(".jar")) continue;
                discoverJar(file, classLoader);
            } catch (Exception e) {
                LOGGER.warning("Failed to scan mod JAR: " + e.getMessage());
            }
        }
    }

    private static void discoverJar(java.io.File jarFile, ClassLoader classLoader) {
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (!name.endsWith(".class") || name.contains("$")) continue;
                String className = name.replace('/', '.').substring(0, name.length() - 6);
                try {
                    Class<?> cls = classLoader.loadClass(className);
                    Mod modAnn = cls.getAnnotation(Mod.class);
                    if (modAnn == null) continue;
                    loadMod(cls, modAnn);
                } catch (Throwable t) {
                    LOGGER.warning("Could not load class " + className + ": " + t.getMessage());
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Failed to open JAR " + jarFile + ": " + e.getMessage());
        }
    }

    private static void loadMod(Class<?> cls, Mod modAnn) {
        try {
            Object instance = cls.getDeclaredConstructor().newInstance();
            String modId = modAnn.modid();
            String name = modAnn.name().isEmpty() ? modId : modAnn.name();
            String version = modAnn.version();

            SimpleModContainer container = new SimpleModContainer(modId, name, version);

            FMLPreInitializationEvent preInit = new FMLPreInitializationEvent();
            preInit.setModContainer(container);
            FMLInitializationEvent init = new FMLInitializationEvent();
            init.setModContainer(container);
            FMLPostInitializationEvent postInit = new FMLPostInitializationEvent();
            postInit.setModContainer(container);

            callEventHandlers(instance, preInit);
            callEventHandlers(instance, init);
            callEventHandlers(instance, postInit);

            Loader.instance().registerMod(container);
            LOGGER.info("Loaded mod: " + name + " (" + modId + ") " + version);
        } catch (Exception e) {
            LOGGER.warning("Failed to instantiate mod " + cls.getName() + ": " + e.getMessage());
        }
    }

    private static void callEventHandlers(Object instance, FMLStateEvent event) {
        for (Method m : instance.getClass().getMethods()) {
            if (!m.isAnnotationPresent(Mod.EventHandler.class)) continue;
            Class<?>[] params = m.getParameterTypes();
            if (params.length != 1) continue;
            if (!params[0].isAssignableFrom(event.getClass())) continue;
            try {
                m.invoke(instance, event);
            } catch (Exception e) {
                LOGGER.warning("Error in @EventHandler " + m.getName() + ": " + e.getMessage());
            }
        }
    }

    private static class SimpleModContainer implements ModContainer {
        private final String modId;
        private final String name;
        private final String version;

        SimpleModContainer(String modId, String name, String version) {
            this.modId = modId;
            this.name = name;
            this.version = version;
        }

        @Override public String getModId() { return modId; }
        @Override public String getName() { return name; }
        @Override public String getVersion() { return version; }
    }
}
