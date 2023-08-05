package org.lwjglx;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

@SuppressWarnings("UnusedReturnValue")
public class Sys {

    public static long getTime() {
        return (long) GLFW.glfwGetTime();
    }

    public static long getTimerResolution() {
        return GLFW.glfwGetTimerFrequency();
    }

    public static boolean openURL(String url) {
        // Attempt to use Webstart if we have it available
        try {
            // Lookup the javax.jnlp.BasicService object
            final Class<?> serviceManagerClass = Class.forName("javax.jnlp.ServiceManager");
            Method lookupMethod = AccessController.doPrivileged((PrivilegedExceptionAction<Method>) () -> serviceManagerClass.getMethod("lookup", String.class));
            Object basicService = lookupMethod.invoke(serviceManagerClass, "javax.jnlp.BasicService");
            final Class<?> basicServiceClass = Class.forName("javax.jnlp.BasicService");
            Method showDocumentMethod = AccessController.doPrivileged((PrivilegedExceptionAction<Method>) () -> basicServiceClass.getMethod("showDocument", URL.class));
            try {
                return (Boolean) showDocumentMethod.invoke(basicService, new URL(url));
            } catch (MalformedURLException e) {
                e.printStackTrace(System.err);
                return false;
            }
        } catch (Exception ue) {
            ue.printStackTrace();
            return false;
        }
    }

    public static String getVersion() {
        return "LWJGL3 Port by Juli15 Version 2.0 stable, based on LWJGL" + Version.getVersion();
    }
}
