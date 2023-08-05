package org.lwjglx.input;

import org.lwjglx.BufferUtils;
import org.lwjglx.opengl.Display;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {

    public static final int KEY_NONE = 0x00;

    public static final int KEY_ESCAPE = 0x01;
    public static final int KEY_1 = 0x02;
    public static final int KEY_2 = 0x03;
    public static final int KEY_3 = 0x04;
    public static final int KEY_4 = 0x05;
    public static final int KEY_5 = 0x06;
    public static final int KEY_6 = 0x07;
    public static final int KEY_7 = 0x08;
    public static final int KEY_8 = 0x09;
    public static final int KEY_9 = 0x0A;
    public static final int KEY_0 = 0x0B;
    public static final int KEY_MINUS = 0x0C; /* - on main keyboard */
    public static final int KEY_EQUALS = 0x0D;
    public static final int KEY_BACK = 0x0E; /* backspace */
    public static final int KEY_TAB = 0x0F;
    public static final int KEY_Q = 0x10;
    public static final int KEY_W = 0x11;
    public static final int KEY_E = 0x12;
    public static final int KEY_R = 0x13;
    public static final int KEY_T = 0x14;
    public static final int KEY_Y = 0x15;
    public static final int KEY_U = 0x16;
    public static final int KEY_I = 0x17;
    public static final int KEY_O = 0x18;
    public static final int KEY_P = 0x19;
    public static final int KEY_LBRACKET = 0x1A;
    public static final int KEY_RBRACKET = 0x1B;
    public static final int KEY_RETURN = 0x1C; /* Enter on main keyboard */
    public static final int KEY_LCONTROL = 0x1D;
    public static final int KEY_A = 0x1E;
    public static final int KEY_S = 0x1F;
    public static final int KEY_D = 0x20;
    public static final int KEY_F = 0x21;
    public static final int KEY_G = 0x22;
    public static final int KEY_H = 0x23;
    public static final int KEY_J = 0x24;
    public static final int KEY_K = 0x25;
    public static final int KEY_L = 0x26;
    public static final int KEY_SEMICOLON = 0x27;
    public static final int KEY_APOSTROPHE = 0x28;
    public static final int KEY_GRAVE = 0x29; /* accent grave */
    public static final int KEY_LSHIFT = 0x2A;
    public static final int KEY_BACKSLASH = 0x2B;
    public static final int KEY_Z = 0x2C;
    public static final int KEY_X = 0x2D;
    public static final int KEY_C = 0x2E;
    public static final int KEY_V = 0x2F;
    public static final int KEY_B = 0x30;
    public static final int KEY_N = 0x31;
    public static final int KEY_M = 0x32;
    public static final int KEY_COMMA = 0x33;
    public static final int KEY_PERIOD = 0x34; /* . on main keyboard */
    public static final int KEY_SLASH = 0x35; /* / on main keyboard */
    public static final int KEY_RSHIFT = 0x36;
    public static final int KEY_MULTIPLY = 0x37; /* * on numeric keypad */
    public static final int KEY_LMENU = 0x38; /* left Alt */
    public static final int KEY_SPACE = 0x39;
    public static final int KEY_CAPITAL = 0x3A;
    public static final int KEY_F1 = 0x3B;
    public static final int KEY_F2 = 0x3C;
    public static final int KEY_F3 = 0x3D;
    public static final int KEY_F4 = 0x3E;
    public static final int KEY_F5 = 0x3F;
    public static final int KEY_F6 = 0x40;
    public static final int KEY_F7 = 0x41;
    public static final int KEY_F8 = 0x42;
    public static final int KEY_F9 = 0x43;
    public static final int KEY_F10 = 0x44;
    public static final int KEY_NUMLOCK = 0x45;
    public static final int KEY_SCROLL = 0x46; /* Scroll Lock */
    public static final int KEY_NUMPAD7 = 0x47;
    public static final int KEY_NUMPAD8 = 0x48;
    public static final int KEY_NUMPAD9 = 0x49;
    public static final int KEY_SUBTRACT = 0x4A; /* - on numeric keypad */
    public static final int KEY_NUMPAD4 = 0x4B;
    public static final int KEY_NUMPAD5 = 0x4C;
    public static final int KEY_NUMPAD6 = 0x4D;
    public static final int KEY_ADD = 0x4E; /* + on numeric keypad */
    public static final int KEY_NUMPAD1 = 0x4F;
    public static final int KEY_NUMPAD2 = 0x50;
    public static final int KEY_NUMPAD3 = 0x51;
    public static final int KEY_NUMPAD0 = 0x52;
    public static final int KEY_DECIMAL = 0x53; /* . on numeric keypad */
    public static final int KEY_F11 = 0x57;
    public static final int KEY_F12 = 0x58;
    public static final int KEY_F13 = 0x64; /*                     (NEC PC98) */
    public static final int KEY_F14 = 0x65; /*                     (NEC PC98) */
    public static final int KEY_F15 = 0x66; /*                     (NEC PC98) */
    public static final int KEY_F16 = 0x67; /* Extended Function keys - (Mac) */
    public static final int KEY_F17 = 0x68;
    public static final int KEY_F18 = 0x69;
    public static final int KEY_KANA = 0x70; /* (Japanese keyboard)            */
    public static final int KEY_F19 = 0x71; /* Extended Function keys - (Mac) */
    public static final int KEY_CONVERT = 0x79; /* (Japanese keyboard)            */
    public static final int KEY_NOCONVERT = 0x7B; /* (Japanese keyboard)            */
    public static final int KEY_YEN = 0x7D; /* (Japanese keyboard)            */
    public static final int KEY_NUMPADEQUALS = 0x8D; /* = on numeric keypad (NEC PC98) */
    public static final int KEY_CIRCUMFLEX = 0x90; /* (Japanese keyboard)            */
    public static final int KEY_AT = 0x91; /*                     (NEC PC98) */
    public static final int KEY_COLON = 0x92; /*                     (NEC PC98) */
    public static final int KEY_UNDERLINE = 0x93; /*                     (NEC PC98) */
    public static final int KEY_KANJI = 0x94; /* (Japanese keyboard)            */
    public static final int KEY_STOP = 0x95; /*                     (NEC PC98) */
    public static final int KEY_AX = 0x96; /*                     (Japan AX) */
    public static final int KEY_UNLABELED = 0x97; /*                        (J3100) */
    public static final int KEY_NUMPADENTER = 0x9C; /* Enter on numeric keypad */
    public static final int KEY_RCONTROL = 0x9D;
    public static final int KEY_SECTION = 0xA7; /* Section symbol (Mac) */
    public static final int KEY_NUMPADCOMMA = 0xB3; /* , on numeric keypad (NEC PC98) */
    public static final int KEY_DIVIDE = 0xB5; /* / on numeric keypad */
    public static final int KEY_SYSRQ = 0xB7;
    public static final int KEY_RMENU = 0xB8; /* right Alt */
    public static final int KEY_FUNCTION = 0xC4; /* Function (Mac) */
    public static final int KEY_PAUSE = 0xC5; /* Pause */
    public static final int KEY_HOME = 0xC7; /* Home on arrow keypad */
    public static final int KEY_UP = 0xC8; /* UpArrow on arrow keypad */
    public static final int KEY_PRIOR = 0xC9; /* PgUp on arrow keypad */
    public static final int KEY_LEFT = 0xCB; /* LeftArrow on arrow keypad */
    public static final int KEY_RIGHT = 0xCD; /* RightArrow on arrow keypad */
    public static final int KEY_END = 0xCF; /* End on arrow keypad */
    public static final int KEY_DOWN = 0xD0; /* DownArrow on arrow keypad */
    public static final int KEY_NEXT = 0xD1; /* PgDn on arrow keypad */
    public static final int KEY_INSERT = 0xD2; /* Insert on arrow keypad */
    public static final int KEY_DELETE = 0xD3; /* Delete on arrow keypad */
    public static final int KEY_CLEAR = 0xDA; /* Clear key (Mac) */
    public static final int KEY_LMETA = 0xDB; /* Left Windows/Option key */
    /**
     * The left windows key, mapped to KEY_LMETA
     *
     * @deprecated Use KEY_LMETA instead
     */
    public static final int KEY_LWIN = KEY_LMETA; /* Left Windows key */
    public static final int KEY_RMETA = 0xDC; /* Right Windows/Option key */
    /**
     * The right windows key, mapped to KEY_RMETA
     *
     * @deprecated Use KEY_RMETA instead
     */
    public static final int KEY_RWIN = KEY_RMETA; /* Right Windows key */
    public static final int KEY_APPS = 0xDD; /* AppMenu key */
    public static final int KEY_POWER = 0xDE;
    public static final int KEY_SLEEP = 0xDF;

    public static final int KEYBOARD_SIZE = 256;

    public static final ByteBuffer keyDownBuffer = BufferUtils.createByteBuffer(KEYBOARD_SIZE);

    private static final int BUFFER_SIZE = 50;
    public static final int EVENT_SIZE = 4 + 1 + 4 + 8 + 1;
    private static ByteBuffer readBuffer;
    private static boolean created;
    private static boolean repeat_enabled;

    /**
     * Key names
     */
    private static final String[] keyName = new String[KEYBOARD_SIZE];
    private static final Map<String, Integer> keyMap = new HashMap<>(KEYBOARD_SIZE);
    private static final Map<Integer, Integer> glfwKeys = new HashMap<>(348);
    private static int counter;

    private static final KeyEvent current_event = new KeyEvent();


    static {
        // Use reflection to find out key names
        Field[] fields = Keyboard.class.getFields();
        try {
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())
                        && Modifier.isPublic(field.getModifiers())
                        && Modifier.isFinal(field.getModifiers())
                        && field.getType().equals(int.class)
                        && field.getName().startsWith("KEY_")
                        && !field.getName().endsWith("WIN")) { /* Don't use deprecated names */

                    int key = field.getInt(null);
                    String name = field.getName().substring(4);
                    keyName[key] = name;
                    keyMap.put(name, key);
                    counter++;
                }
            }
        } catch (Exception e) {
        }
    }
    public static int getKeyCount() {
        return counter;
    }

    public static String getKeyName(int key) {
        return keyName[key];
    }

    public static int getKeyIndex(String keyName) {
        Integer ret = keyMap.get(keyName);
        if (ret == null)
            return KEY_NONE;
        else
            return ret;
    }

    public static void create() {
        if(created)
            return;

        createGLFWKeyMap();
        created = true;
        repeat_enabled = false;
        readBuffer = ByteBuffer.allocate(EVENT_SIZE * BUFFER_SIZE);
        reset();;
    }

    public static void destroy() {
        if (!created)
            return;
        created = false;
        reset();
    }

    public static boolean next() {
        if (!created)
            throw new IllegalStateException("Keyboard must be created before you can read events");
        boolean result;
        while ((result = readNext(current_event)) && current_event.repeat && !repeat_enabled)
            ;
        return result;

    }

    private static boolean readNext(KeyEvent event) {
        if (readBuffer.hasRemaining()) {
            event.key = readBuffer.getInt() & 0xFF;
            event.state = readBuffer.get() != 0;
            event.character = readBuffer.getChar();
            boolean repeat = readBuffer.get() == 1;
            if (repeat_enabled) event.repeat = repeat;
            return true;
        } else
            return false;
    }

    public static void enableRepeatEvents(boolean enable) {
        System.out.println("SetRepeat: " + enable);
        repeat_enabled = enable;
    }

    public static int getEventKey() {
        return current_event.key;
    }

    public static boolean getEventKeyState() {
        return current_event.state;
    }

    public static char getEventCharacter() {
        return (char) current_event.character;
    }

    public static boolean isRepeatEvent() {
        return repeat_enabled;
    }

    public static boolean isCreated() {
        return created;
    }

    public static boolean isKeyDown(int key) {
        if (!created)
            throw new IllegalStateException("Keyboard must be created before you can query key state");
        return keyDownBuffer.get(key) != 0;
    }

    public static void reset() {
        readBuffer.limit(0);
        for (int i = 0; i < keyDownBuffer.remaining(); i++)
            keyDownBuffer.put(i, (byte) 0);
        current_event.reset();
    }

    public static void pollGLFW(){
        glfwSetCharCallback(Display.getWindow(), new GLFWCharCallback() {
            @Override
            public void invoke(long l, int i) {
                parseGLFWCodes(0, 1, i);
            }
        });
        glfwSetKeyCallback(Display.getWindow(), new GLFWKeyCallback() {
            @Override
            public void invoke(long l, int key, int scancode, int action, int modifierBits) {
                    if(!repeat_enabled && action == 2) return;
                    int poll_key = glfwKeys.get(key);
                    parseGLFWCodes(poll_key, action,0 );

                    keyDownBuffer.compact();
                    keyDownBuffer.put(poll_key, (byte) ((action == 2 || action == 1) ? 1 : 0));
                    keyDownBuffer.flip();
            }
        });
    }


    public static void parseGLFWCodes(int key, int action,int character) {
        readBuffer.compact();
        readBuffer.putInt(key);
        readBuffer.put((byte) ((action == 2 || action == 1) ? 1 : 0));
        readBuffer.putChar((char) character);
        readBuffer.put((byte) ((action == 2) ? 1 : 0));
        readBuffer.flip();
    }

    private static void createGLFWKeyMap() {
        glfwKeys.put(GLFW_KEY_UNKNOWN, KEY_NONE);
        glfwKeys.put(GLFW_KEY_SPACE, KEY_SPACE);
        glfwKeys.put(GLFW_KEY_APOSTROPHE, KEY_APOSTROPHE);
        glfwKeys.put(GLFW_KEY_COMMA, KEY_COMMA);
        glfwKeys.put(GLFW_KEY_MINUS, KEY_MINUS);
        glfwKeys.put(GLFW_KEY_PERIOD, KEY_PERIOD);
        glfwKeys.put(GLFW_KEY_SLASH, KEY_SLASH);
        glfwKeys.put(GLFW_KEY_0, KEY_0);
        glfwKeys.put(GLFW_KEY_1, KEY_1);
        glfwKeys.put(GLFW_KEY_2, KEY_2);
        glfwKeys.put(GLFW_KEY_3, KEY_3);
        glfwKeys.put(GLFW_KEY_4, KEY_4);
        glfwKeys.put(GLFW_KEY_5, KEY_5);
        glfwKeys.put(GLFW_KEY_6, KEY_6);
        glfwKeys.put(GLFW_KEY_7, KEY_7);
        glfwKeys.put(GLFW_KEY_8, KEY_8);
        glfwKeys.put(GLFW_KEY_9, KEY_9);
        glfwKeys.put(GLFW_KEY_SEMICOLON, KEY_SEMICOLON);
        glfwKeys.put(GLFW_KEY_EQUAL, KEY_EQUALS);
        glfwKeys.put(GLFW_KEY_A, KEY_A);
        glfwKeys.put(GLFW_KEY_B, KEY_B);
        glfwKeys.put(GLFW_KEY_C, KEY_C);
        glfwKeys.put(GLFW_KEY_D, KEY_D);
        glfwKeys.put(GLFW_KEY_E, KEY_E);
        glfwKeys.put(GLFW_KEY_F, KEY_F);
        glfwKeys.put(GLFW_KEY_G, KEY_G);
        glfwKeys.put(GLFW_KEY_H, KEY_H);
        glfwKeys.put(GLFW_KEY_I, KEY_I);
        glfwKeys.put(GLFW_KEY_J, KEY_J);
        glfwKeys.put(GLFW_KEY_K, KEY_K);
        glfwKeys.put(GLFW_KEY_L, KEY_L);
        glfwKeys.put(GLFW_KEY_M, KEY_M);
        glfwKeys.put(GLFW_KEY_N, KEY_N);
        glfwKeys.put(GLFW_KEY_O, KEY_O);
        glfwKeys.put(GLFW_KEY_P, KEY_P);
        glfwKeys.put(GLFW_KEY_Q, KEY_Q);
        glfwKeys.put(GLFW_KEY_R, KEY_R);
        glfwKeys.put(GLFW_KEY_S, KEY_S);
        glfwKeys.put(GLFW_KEY_T, KEY_T);
        glfwKeys.put(GLFW_KEY_U, KEY_U);
        glfwKeys.put(GLFW_KEY_V, KEY_V);
        glfwKeys.put(GLFW_KEY_W, KEY_W);
        glfwKeys.put(GLFW_KEY_X, KEY_X);
        glfwKeys.put(GLFW_KEY_Y, KEY_Y);
        glfwKeys.put(GLFW_KEY_Z, KEY_Z);
        glfwKeys.put(GLFW_KEY_LEFT_BRACKET, KEY_LBRACKET);
        glfwKeys.put(GLFW_KEY_BACKSLASH, KEY_BACKSLASH);
        glfwKeys.put(GLFW_KEY_RIGHT_BRACKET, KEY_RBRACKET);
        glfwKeys.put(GLFW_KEY_GRAVE_ACCENT, KEY_GRAVE);
        //IDK which keys this are, but there are not on US Layout
        //glfwKeys.put(GLFW_KEY_WORLD_1,KEY_WORLD_1);
        //glfwKeys.put(GLFW_KEY_WORLD_2,KEY_WORLD_2);
        glfwKeys.put(GLFW_KEY_ESCAPE, KEY_ESCAPE);
        glfwKeys.put(GLFW_KEY_ENTER, KEY_RETURN);
        glfwKeys.put(GLFW_KEY_TAB, KEY_TAB);
        glfwKeys.put(GLFW_KEY_BACKSPACE, KEY_BACK);
        glfwKeys.put(GLFW_KEY_INSERT, KEY_INSERT);
        glfwKeys.put(GLFW_KEY_DELETE, KEY_DELETE);
        glfwKeys.put(GLFW_KEY_RIGHT, KEY_RIGHT);
        glfwKeys.put(GLFW_KEY_LEFT, KEY_LEFT);
        glfwKeys.put(GLFW_KEY_DOWN, KEY_DOWN);
        glfwKeys.put(GLFW_KEY_UP, KEY_UP);
        glfwKeys.put(GLFW_KEY_PAGE_UP, KEY_PRIOR);
        glfwKeys.put(GLFW_KEY_PAGE_DOWN, KEY_NEXT);
        glfwKeys.put(GLFW_KEY_HOME, KEY_HOME);
        glfwKeys.put(GLFW_KEY_END, KEY_END);
        glfwKeys.put(GLFW_KEY_CAPS_LOCK, KEY_CAPITAL);
        glfwKeys.put(GLFW_KEY_SCROLL_LOCK, KEY_SCROLL);
        glfwKeys.put(GLFW_KEY_NUM_LOCK, KEY_NUMLOCK);
        glfwKeys.put(GLFW_KEY_PRINT_SCREEN, KEY_SYSRQ);
        glfwKeys.put(GLFW_KEY_PAUSE, KEY_PAUSE);
        glfwKeys.put(GLFW_KEY_F1, KEY_F1);
        glfwKeys.put(GLFW_KEY_F2, KEY_F2);
        glfwKeys.put(GLFW_KEY_F3, KEY_F3);
        glfwKeys.put(GLFW_KEY_F4, KEY_F4);
        glfwKeys.put(GLFW_KEY_F5, KEY_F5);
        glfwKeys.put(GLFW_KEY_F6, KEY_F6);
        glfwKeys.put(GLFW_KEY_F7, KEY_F7);
        glfwKeys.put(GLFW_KEY_F8, KEY_F8);
        glfwKeys.put(GLFW_KEY_F9, KEY_F9);
        glfwKeys.put(GLFW_KEY_F10, KEY_F10);
        glfwKeys.put(GLFW_KEY_F11, KEY_F11);
        glfwKeys.put(GLFW_KEY_F12, KEY_F12);
        glfwKeys.put(GLFW_KEY_F13, KEY_F13);
        glfwKeys.put(GLFW_KEY_F14, KEY_F14);
        glfwKeys.put(GLFW_KEY_F15, KEY_F15);
        glfwKeys.put(GLFW_KEY_F16, KEY_F16);
        glfwKeys.put(GLFW_KEY_F17, KEY_F17);
        glfwKeys.put(GLFW_KEY_F18, KEY_F18);
        glfwKeys.put(GLFW_KEY_F19, KEY_F19);
        //lwjgl2 doesnt suport all F keys
        //glfwKeys.put(GLFW_KEY_F20,KEY_F20);
        //glfwKeys.put(GLFW_KEY_F21,KEY_F21);
        //glfwKeys.put(GLFW_KEY_F22,KEY_F22);
        //glfwKeys.put(GLFW_KEY_F23,KEY_F23);
        //glfwKeys.put(GLFW_KEY_F24,KEY_F24);
        //glfwKeys.put(GLFW_KEY_F25,KEY_F25);
        glfwKeys.put(GLFW_KEY_KP_0, KEY_NUMPAD0);
        glfwKeys.put(GLFW_KEY_KP_1, KEY_NUMPAD1);
        glfwKeys.put(GLFW_KEY_KP_2, KEY_NUMPAD2);
        glfwKeys.put(GLFW_KEY_KP_3, KEY_NUMPAD3);
        glfwKeys.put(GLFW_KEY_KP_4, KEY_NUMPAD4);
        glfwKeys.put(GLFW_KEY_KP_5, KEY_NUMPAD5);
        glfwKeys.put(GLFW_KEY_KP_6, KEY_NUMPAD6);
        glfwKeys.put(GLFW_KEY_KP_7, KEY_NUMPAD7);
        glfwKeys.put(GLFW_KEY_KP_8, KEY_NUMPAD8);
        glfwKeys.put(GLFW_KEY_KP_9, KEY_NUMPAD9);
        glfwKeys.put(GLFW_KEY_KP_DECIMAL, KEY_DECIMAL);
        glfwKeys.put(GLFW_KEY_KP_DIVIDE, KEY_DIVIDE);
        glfwKeys.put(GLFW_KEY_KP_MULTIPLY, KEY_MULTIPLY);
        glfwKeys.put(GLFW_KEY_KP_SUBTRACT, KEY_SUBTRACT);
        glfwKeys.put(GLFW_KEY_KP_ADD, KEY_ADD);
        glfwKeys.put(GLFW_KEY_KP_ENTER, KEY_NUMPADENTER);
        glfwKeys.put(GLFW_KEY_KP_EQUAL, KEY_NUMPADEQUALS);
        glfwKeys.put(GLFW_KEY_LEFT_SHIFT, KEY_LSHIFT);
        glfwKeys.put(GLFW_KEY_LEFT_CONTROL, KEY_LCONTROL);
        glfwKeys.put(GLFW_KEY_LEFT_ALT, KEY_LMENU);
        glfwKeys.put(GLFW_KEY_LEFT_SUPER, KEY_LMETA);
        glfwKeys.put(GLFW_KEY_RIGHT_SHIFT, KEY_RSHIFT);
        glfwKeys.put(GLFW_KEY_RIGHT_CONTROL, KEY_RCONTROL);
        glfwKeys.put(GLFW_KEY_RIGHT_ALT, KEY_RMENU);
        glfwKeys.put(GLFW_KEY_RIGHT_SUPER, KEY_RMETA);
        glfwKeys.put(GLFW_KEY_MENU, KEY_RMENU);
    }


    private static final class KeyEvent {
        /**
         * The current keyboard character being examined
         */
        private int character;

        /**
         * The current keyboard event key being examined
         */
        private int key;

        /**
         * The current state of the key being examined in the event queue
         */
        private boolean state;

        /**
         * Is the current event a repeated event?
         */
        private boolean repeat;

        private void reset() {
            character = 0;
            key = 0;
            state = false;
            repeat = false;
        }
    }

}
