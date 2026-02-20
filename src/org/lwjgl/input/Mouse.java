package org.lwjgl.input;

public class Mouse {

    public static void create() {
        org.lwjglx.input.Mouse.create();
    }

    public static void destroy() {
        org.lwjglx.input.Mouse.destroy();
    }

    public static boolean next() {
        return org.lwjglx.input.Mouse.next();
    }

    public static int getEventButton() {
        return org.lwjglx.input.Mouse.getEventButton();
    }

    public static boolean getEventButtonState() {
        return org.lwjglx.input.Mouse.getEventButtonState();
    }

    public static int getEventX() {
        return org.lwjglx.input.Mouse.getEventX();
    }

    public static int getEventY() {
        return org.lwjglx.input.Mouse.getEventY();
    }

    public static int getEventDX() {
        return org.lwjglx.input.Mouse.getDX();
    }

    public static int getEventDY() {
        return org.lwjglx.input.Mouse.getDY();
    }

    public static int getEventDWheel() {
        return org.lwjglx.input.Mouse.getEventDWheel();
    }

    public static long getEventNanoseconds() {
        return System.nanoTime();
    }

    public static int getX() {
        return org.lwjglx.input.Mouse.getX();
    }

    public static int getY() {
        return org.lwjglx.input.Mouse.getY();
    }

    public static int getDX() {
        return org.lwjglx.input.Mouse.getDX();
    }

    public static int getDY() {
        return org.lwjglx.input.Mouse.getDY();
    }

    public static int getDWheel() {
        return org.lwjglx.input.Mouse.getDWheel();
    }

    public static boolean isButtonDown(int button) {
        return org.lwjglx.input.Mouse.isButtonDown(button);
    }

    public static void setGrabbed(boolean grab) {
        org.lwjglx.input.Mouse.setGrabbed(grab);
    }

    public static boolean isGrabbed() {
        return org.lwjglx.input.Mouse.isGrabbed();
    }

    public static boolean isCreated() {
        return org.lwjglx.input.Mouse.isCreated();
    }

    public static boolean isInsideWindow() {
        return org.lwjglx.input.Mouse.isInsideWindow();
    }

    public static void setCursorPosition(int x, int y) {
        org.lwjglx.input.Mouse.setCursorPosition(x, y);
    }

    public static void setClipMouseCoordinatesToWindow(boolean clip) {
        org.lwjglx.input.Mouse.setClipMouseCoordinatesToWindow(clip);
    }

    public static String getButtonName(int button) {
        return org.lwjglx.input.Mouse.getButtonName(button);
    }

    public static int getButtonIndex(String buttonName) {
        return org.lwjglx.input.Mouse.getButtonIndex(buttonName);
    }

    public static int getButtonCount() {
        return 8;
    }

    public static boolean hasWheel() {
        return true;
    }

    /** No-op: event queue is driven by GLFW callbacks. */
    public static void poll() {
    }
}
