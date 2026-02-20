package org.lwjgl;

import org.lwjgl.glfw.GLFW;

public class Sys {
    public static long getTimerResolution() {
        return GLFW.glfwGetTimerFrequency();
    }

    public static long getTime() {
        return (long)(GLFW.glfwGetTime() * getTimerResolution());
    }

    public static String getVersion() {
        return org.lwjglx.Sys.getVersion();
    }

    public static boolean openURL(String url) {
        return org.lwjglx.Sys.openURL(url);
    }
}
