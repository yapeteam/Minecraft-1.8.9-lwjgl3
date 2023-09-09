package org.lwjglx.input;

import net.minecraft.client.Minecraft;
import org.lwjglx.opengl.Display;
import org.lwjglx.BufferUtils;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {

    private static boolean created;

    private static String[]	buttonName;
    private static final Map<String, Integer> buttonMap	= new HashMap<>(8);
    private static ByteBuffer readBuffer;
    private static ByteBuffer	buttons;
    private static final int BUFFER_SIZE = 50;
    //Max Nummber of buttons glfw supports https://www.glfw.org/docs/3.3/group__buttons.html
    private static final int BUTTON_COUNT = 8;
    //int = 4 boolean = 1, long = 8
    // 4 + 1 + 4 + 4 + 4 + 4 + 4
    public static final int EVENT_SIZE = 4 + 1 + 4 + 4 + 4 + 4 + 4;
    private static MouseEvent current_Event;
    private static boolean isGrabbed;

    private static int last_event_raw_x;
    private static int last_event_raw_y;
    private static  boolean clipMouseCoordinatesToWindow;

    private static int poll_button = 0;
    private static int poll_action = 0;
    public static int poll_xPos = 0;
    public static int poll_yPos = 0;
    public static int poll_scrollY = 0;
    private static int current_dx = 0;
    private static int current_dy = 0;
    public static int last_x = 0;
    public static int last_y = 0;
    private static boolean poll_outside;
    private static boolean pollNeed;


    public static void pollGLFW(){
        if(!created) return;
          float sensibility = (Minecraft.getMinecraft().gameSettings.mouseSensitivity) * 0.6F + 0.2F;
        if(sensibility > 1.0F){
            sensibility = Math.min(sensibility, 1.0F);
        }
        final float fixed = sensibility * sensibility * sensibility * 8.0F;
        glfwSetMouseButtonCallback(Display.getWindow(), new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long l, int i, int i1, int i2) {
                poll_button = i;
                poll_action = i1;
                buttons.put(i, (byte)i1);
                pollNeed = true;
            }
        });
        glfwSetCursorPosCallback(Display.getWindow(), new GLFWCursorPosCallback() {
            @Override
            public void invoke(long l, double v, double v1) {
                if(Display.isActive()){
                    last_x = poll_xPos;
                    last_y = poll_yPos;
                    poll_xPos = (int) v;
                    poll_yPos = (int) (Display.getHeight() - v1);
                    current_dx = (int) ((poll_xPos - last_x) * (fixed));
                    current_dy = (int) ((poll_yPos - last_y) * fixed);
                    pollNeed = true;
                }
            }
        });
        glfwSetScrollCallback(Display.getWindow(), new GLFWScrollCallback() {
            @Override
            public void invoke(long l, double v, double v1) {
                poll_scrollY = (int) v1;
                pollNeed = true;
            }
        });
        glfwSetCursorEnterCallback(Display.getWindow(), new GLFWCursorEnterCallback() {
            @Override
            public void invoke(long l, boolean entered) {
                poll_outside = entered;
            }
        });
    }
    public static void createEvent(){
        if(!created || !pollNeed) return;
        readBuffer.compact();
        pollNeed = false;
        readBuffer.putInt(poll_button);
        readBuffer.put((byte)poll_action);
        if(isGrabbed){
            readBuffer.putInt(normalize(poll_xPos - last_x));
            readBuffer.putInt(normalize(poll_yPos - last_y));
        }else {
            readBuffer.putInt(poll_xPos);
            readBuffer.putInt(poll_yPos);
        }
        readBuffer.putInt(poll_scrollY);
        readBuffer.flip();
        //To prevent doubleclick when moving
        poll_button = -1;
        poll_action = 0;
    }



    public static void create(){
        if(created) return;
        buttons = BufferUtils.createByteBuffer(BUTTON_COUNT);
        readBuffer = ByteBuffer.allocate(EVENT_SIZE * BUFFER_SIZE);
        readBuffer.limit(0);
        clipMouseCoordinatesToWindow = true;
        created = true;

        buttonName = new String[8];
        for (int i = 0; i < 8; i++) {
            buttonName[i] = "BUTTON" + i;
            buttonMap.put(buttonName[i], i);
        }

    }
    public static boolean next(){
        if(!created) throw new IllegalStateException("Mouse must be created before you can read events");
        if(readBuffer.hasRemaining()){
            MouseEvent event = new MouseEvent();
            event.eventButton = readBuffer.getInt();
            event.eventState = readBuffer.get() != 0;

            if(isGrabbed){
                event.dx = readBuffer.getInt();
                event.dy = readBuffer.getInt();
                event.x += event.dx;
                event.y += event.dy;
                last_event_raw_x = event.x;
                last_event_raw_y = event.y;
            }else{
                int new_event_x = readBuffer.getInt();
                int new_event_y = readBuffer.getInt();
                event.dx = new_event_x - last_event_raw_x;
                event.dy = new_event_y - last_event_raw_y;
                event.x = new_event_x;
                event.y = new_event_y;
                last_event_raw_x = new_event_x;
                last_event_raw_y = new_event_y;
            }
            if(clipMouseCoordinatesToWindow) {
                event.x = Math.min(Display.getWidth() - 1, Math.max(0, event.x));
                event.y = Math.min(Display.getHeight() - 1, Math.max(0, event.y));
            }
            event.event_dwheel = readBuffer.getInt();
            current_Event = event;
            return true;
        }
        return false;
    }


    public static int getButtonIndex(String buttonName) {
        Integer ret = buttonMap.get(buttonName);
        if (ret == null)
            return -1;
        else
            return ret;
    }

    public static String getButtonName(int button) {
        if (button >= buttonName.length || button < 0)
            return null;
        else
            return buttonName[button];
    }

    public static boolean isInsideWindow() {
        return poll_outside;
    }
    public static int getEventButton(){
        return current_Event.eventButton;
    }
    public static boolean getEventButtonState(){
        return current_Event.eventState;
    }
    public static int getEventDWheel(){
        return current_Event.event_dwheel;
    }
    public static int getEventX(){
        return current_Event.x;
    }
    public static int getEventY(){
        return current_Event.y;
    }
    public static boolean isCreated(){
        return created;
    }
    public static int getX(){
        return Math.min(Math.max(poll_xPos, 0), Display.getWidth() - 1);
    }
    public static int getY(){
        return Math.min(Math.max(poll_yPos, 0), Display.getHeight() - 1);
    }
    public static void setClipMouseCoordinatesToWindow(boolean clip) {
        clipMouseCoordinatesToWindow = clip;
    }
    public static boolean isButtonDown(int button){
        if (!created) throw new IllegalStateException("Mouse must be created before you can poll the button state");
        if (button >= BUTTON_COUNT || button < 0)
            return false;
        else
            return buttons.get(button) == 1;
    }
    public static void setGrabbed(boolean grab){
        if (!created) throw new IllegalStateException("Mouse must be created before you can poll the button state");
        isGrabbed = grab;
        if(!grab) glfwSetInputMode(Display.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        else glfwSetInputMode(Display.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwSetCursorPos(Display.getWindow(), poll_xPos, poll_yPos);
    }
    public static void setCursorPosition(int x, int y){
        if (!isCreated()) throw new IllegalStateException("Mouse is not created");
        poll_xPos = current_Event.x = x;
        poll_yPos = current_Event.y = y;
        glfwSetCursorPos(Display.getWindow(), x, y);
    }
    public static int getDX(){
        int result = current_dx;
        current_dx = 0;
        return result;
    }
    public static int getDY(){
        int result = current_dy;
        current_dy = 0;
        return result;
    }
    private static int normalize(int input){
        if(input < 0) return input /-1;
        return input;
    }

    public static int getDWheel() {
        return poll_scrollY;
    }


    private static final class MouseEvent {

        private int eventButton;
        private boolean eventState;

        private int dx;
        private int dy;
        private int x;
        private int y;
        private int event_dwheel;

    }
}
