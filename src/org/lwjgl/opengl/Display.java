package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;

import java.nio.ByteBuffer;

public class Display {

    public static DisplayMode getDisplayMode() {
        org.lwjglx.opengl.DisplayMode m = org.lwjglx.opengl.Display.getDisplayMode();
        return toShim(m);
    }

    public static void setDisplayMode(DisplayMode displayMode) {
        org.lwjglx.opengl.Display.setDisplayMode(
                new org.lwjglx.opengl.DisplayMode(displayMode.getWidth(), displayMode.getHeight()));
    }

    public static DisplayMode[] getAvailableDisplayModes() {
        org.lwjglx.opengl.DisplayMode[] src = org.lwjglx.opengl.Display.getAvailableDisplayModes();
        DisplayMode[] result = new DisplayMode[src.length];
        for (int i = 0; i < src.length; i++) result[i] = toShim(src[i]);
        return result;
    }

    public static DisplayMode getDesktopDisplayMode() {
        return toShim(org.lwjglx.opengl.Display.getDesktopDisplayMode());
    }

    public static void setTitle(String title) {
        org.lwjglx.opengl.Display.setTitle(title);
    }

    public static void setFullscreen(boolean fullscreen) {
        org.lwjglx.opengl.Display.setFullscreen(fullscreen);
    }

    public static void setResizable(boolean resizable) {
        org.lwjglx.opengl.Display.setResizable(resizable);
    }

    public static void setIcon(ByteBuffer[] icons) {
        org.lwjglx.opengl.Display.setIcon(icons);
    }

    public static void setVSyncEnabled(boolean vsync) {
        org.lwjglx.opengl.Display.setVSyncEnabled(vsync);
    }

    public static void create() throws LWJGLException {
        try {
            org.lwjglx.opengl.Display.create();
        } catch (org.lwjglx.LWJGLException e) {
            throw new LWJGLException(e.getMessage(), e);
        }
    }

    public static void create(PixelFormat pf) throws LWJGLException {
        try {
            org.lwjglx.opengl.Display.create(pf.toLwjglx());
        } catch (org.lwjglx.LWJGLException e) {
            throw new LWJGLException(e.getMessage(), e);
        }
    }

    public static void create(PixelFormat pf, ContextAttribs ca) throws LWJGLException {
        try {
            org.lwjglx.opengl.Display.create(pf.toLwjglx(), ca.toLwjglx());
        } catch (org.lwjglx.LWJGLException e) {
            throw new LWJGLException(e.getMessage(), e);
        }
    }

    public static void destroy() {
        org.lwjglx.opengl.Display.destroy();
    }

    public static void update() {
        org.lwjglx.opengl.Display.update();
    }

    public static void sync(int fps) {
        org.lwjglx.opengl.Display.sync(fps);
    }

    public static boolean isCreated() {
        return org.lwjglx.opengl.Display.isCreated();
    }

    public static boolean isCloseRequested() {
        return org.lwjglx.opengl.Display.isCloseRequested();
    }

    public static boolean isActive() {
        return org.lwjglx.opengl.Display.isActive();
    }

    public static boolean wasResized() {
        return org.lwjglx.opengl.Display.wasResized();
    }

    public static int getWidth() {
        return org.lwjglx.opengl.Display.getWidth();
    }

    public static int getHeight() {
        return org.lwjglx.opengl.Display.getHeight();
    }

    public static void releaseContext() {
        org.lwjglx.opengl.Display.releaseContext();
    }

    public static long getWindow() {
        return org.lwjglx.opengl.Display.getWindow();
    }

    private static DisplayMode toShim(org.lwjglx.opengl.DisplayMode m) {
        return new DisplayMode(m.getWidth(), m.getHeight(), m.getBitsPerPixel(), m.getFrequency());
    }
}
