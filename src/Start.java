import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import net.minecraft.client.main.Main;

public class Start {
    public static void main(String[] args) {
        File modsDir = new File("mods");
        modsDir.mkdirs();
        File[] jars = modsDir.listFiles(f -> f.getName().endsWith(".jar"));
        URL[] urls = jars == null ? new URL[0] : Arrays.stream(jars)
                .map(f -> { try { return f.toURI().toURL(); } catch (Exception e) { throw new RuntimeException(e); } })
                .toArray(URL[]::new);
        URLClassLoader modLoader = new URLClassLoader(urls, Start.class.getClassLoader());
        Thread.currentThread().setContextClassLoader(modLoader);
        ModDiscovery.discover(modLoader);

        Main.main(concat(new String[]{"--version", "mcp", "--accessToken", "0", "--assetsDir", "assets", "--assetIndex", "1.8", "--userProperties", "{}"}, args));
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
