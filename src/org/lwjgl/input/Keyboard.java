package org.lwjgl.input;

import java.nio.ByteBuffer;

public class Keyboard {

    public static final int KEY_NONE         = org.lwjglx.input.Keyboard.KEY_NONE;
    public static final int KEY_ESCAPE       = org.lwjglx.input.Keyboard.KEY_ESCAPE;
    public static final int KEY_1            = org.lwjglx.input.Keyboard.KEY_1;
    public static final int KEY_2            = org.lwjglx.input.Keyboard.KEY_2;
    public static final int KEY_3            = org.lwjglx.input.Keyboard.KEY_3;
    public static final int KEY_4            = org.lwjglx.input.Keyboard.KEY_4;
    public static final int KEY_5            = org.lwjglx.input.Keyboard.KEY_5;
    public static final int KEY_6            = org.lwjglx.input.Keyboard.KEY_6;
    public static final int KEY_7            = org.lwjglx.input.Keyboard.KEY_7;
    public static final int KEY_8            = org.lwjglx.input.Keyboard.KEY_8;
    public static final int KEY_9            = org.lwjglx.input.Keyboard.KEY_9;
    public static final int KEY_0            = org.lwjglx.input.Keyboard.KEY_0;
    public static final int KEY_MINUS        = org.lwjglx.input.Keyboard.KEY_MINUS;
    public static final int KEY_EQUALS       = org.lwjglx.input.Keyboard.KEY_EQUALS;
    public static final int KEY_BACK         = org.lwjglx.input.Keyboard.KEY_BACK;
    public static final int KEY_TAB          = org.lwjglx.input.Keyboard.KEY_TAB;
    public static final int KEY_Q            = org.lwjglx.input.Keyboard.KEY_Q;
    public static final int KEY_W            = org.lwjglx.input.Keyboard.KEY_W;
    public static final int KEY_E            = org.lwjglx.input.Keyboard.KEY_E;
    public static final int KEY_R            = org.lwjglx.input.Keyboard.KEY_R;
    public static final int KEY_T            = org.lwjglx.input.Keyboard.KEY_T;
    public static final int KEY_Y            = org.lwjglx.input.Keyboard.KEY_Y;
    public static final int KEY_U            = org.lwjglx.input.Keyboard.KEY_U;
    public static final int KEY_I            = org.lwjglx.input.Keyboard.KEY_I;
    public static final int KEY_O            = org.lwjglx.input.Keyboard.KEY_O;
    public static final int KEY_P            = org.lwjglx.input.Keyboard.KEY_P;
    public static final int KEY_LBRACKET     = org.lwjglx.input.Keyboard.KEY_LBRACKET;
    public static final int KEY_RBRACKET     = org.lwjglx.input.Keyboard.KEY_RBRACKET;
    public static final int KEY_RETURN       = org.lwjglx.input.Keyboard.KEY_RETURN;
    public static final int KEY_LCONTROL     = org.lwjglx.input.Keyboard.KEY_LCONTROL;
    public static final int KEY_A            = org.lwjglx.input.Keyboard.KEY_A;
    public static final int KEY_S            = org.lwjglx.input.Keyboard.KEY_S;
    public static final int KEY_D            = org.lwjglx.input.Keyboard.KEY_D;
    public static final int KEY_F            = org.lwjglx.input.Keyboard.KEY_F;
    public static final int KEY_G            = org.lwjglx.input.Keyboard.KEY_G;
    public static final int KEY_H            = org.lwjglx.input.Keyboard.KEY_H;
    public static final int KEY_J            = org.lwjglx.input.Keyboard.KEY_J;
    public static final int KEY_K            = org.lwjglx.input.Keyboard.KEY_K;
    public static final int KEY_L            = org.lwjglx.input.Keyboard.KEY_L;
    public static final int KEY_SEMICOLON    = org.lwjglx.input.Keyboard.KEY_SEMICOLON;
    public static final int KEY_APOSTROPHE   = org.lwjglx.input.Keyboard.KEY_APOSTROPHE;
    public static final int KEY_GRAVE        = org.lwjglx.input.Keyboard.KEY_GRAVE;
    public static final int KEY_LSHIFT       = org.lwjglx.input.Keyboard.KEY_LSHIFT;
    public static final int KEY_BACKSLASH    = org.lwjglx.input.Keyboard.KEY_BACKSLASH;
    public static final int KEY_Z            = org.lwjglx.input.Keyboard.KEY_Z;
    public static final int KEY_X            = org.lwjglx.input.Keyboard.KEY_X;
    public static final int KEY_C            = org.lwjglx.input.Keyboard.KEY_C;
    public static final int KEY_V            = org.lwjglx.input.Keyboard.KEY_V;
    public static final int KEY_B            = org.lwjglx.input.Keyboard.KEY_B;
    public static final int KEY_N            = org.lwjglx.input.Keyboard.KEY_N;
    public static final int KEY_M            = org.lwjglx.input.Keyboard.KEY_M;
    public static final int KEY_COMMA        = org.lwjglx.input.Keyboard.KEY_COMMA;
    public static final int KEY_PERIOD       = org.lwjglx.input.Keyboard.KEY_PERIOD;
    public static final int KEY_SLASH        = org.lwjglx.input.Keyboard.KEY_SLASH;
    public static final int KEY_RSHIFT       = org.lwjglx.input.Keyboard.KEY_RSHIFT;
    public static final int KEY_MULTIPLY     = org.lwjglx.input.Keyboard.KEY_MULTIPLY;
    public static final int KEY_LMENU        = org.lwjglx.input.Keyboard.KEY_LMENU;
    public static final int KEY_SPACE        = org.lwjglx.input.Keyboard.KEY_SPACE;
    public static final int KEY_CAPITAL      = org.lwjglx.input.Keyboard.KEY_CAPITAL;
    public static final int KEY_F1           = org.lwjglx.input.Keyboard.KEY_F1;
    public static final int KEY_F2           = org.lwjglx.input.Keyboard.KEY_F2;
    public static final int KEY_F3           = org.lwjglx.input.Keyboard.KEY_F3;
    public static final int KEY_F4           = org.lwjglx.input.Keyboard.KEY_F4;
    public static final int KEY_F5           = org.lwjglx.input.Keyboard.KEY_F5;
    public static final int KEY_F6           = org.lwjglx.input.Keyboard.KEY_F6;
    public static final int KEY_F7           = org.lwjglx.input.Keyboard.KEY_F7;
    public static final int KEY_F8           = org.lwjglx.input.Keyboard.KEY_F8;
    public static final int KEY_F9           = org.lwjglx.input.Keyboard.KEY_F9;
    public static final int KEY_F10          = org.lwjglx.input.Keyboard.KEY_F10;
    public static final int KEY_NUMLOCK      = org.lwjglx.input.Keyboard.KEY_NUMLOCK;
    public static final int KEY_SCROLL       = org.lwjglx.input.Keyboard.KEY_SCROLL;
    public static final int KEY_NUMPAD7      = org.lwjglx.input.Keyboard.KEY_NUMPAD7;
    public static final int KEY_NUMPAD8      = org.lwjglx.input.Keyboard.KEY_NUMPAD8;
    public static final int KEY_NUMPAD9      = org.lwjglx.input.Keyboard.KEY_NUMPAD9;
    public static final int KEY_SUBTRACT     = org.lwjglx.input.Keyboard.KEY_SUBTRACT;
    public static final int KEY_NUMPAD4      = org.lwjglx.input.Keyboard.KEY_NUMPAD4;
    public static final int KEY_NUMPAD5      = org.lwjglx.input.Keyboard.KEY_NUMPAD5;
    public static final int KEY_NUMPAD6      = org.lwjglx.input.Keyboard.KEY_NUMPAD6;
    public static final int KEY_ADD          = org.lwjglx.input.Keyboard.KEY_ADD;
    public static final int KEY_NUMPAD1      = org.lwjglx.input.Keyboard.KEY_NUMPAD1;
    public static final int KEY_NUMPAD2      = org.lwjglx.input.Keyboard.KEY_NUMPAD2;
    public static final int KEY_NUMPAD3      = org.lwjglx.input.Keyboard.KEY_NUMPAD3;
    public static final int KEY_NUMPAD0      = org.lwjglx.input.Keyboard.KEY_NUMPAD0;
    public static final int KEY_DECIMAL      = org.lwjglx.input.Keyboard.KEY_DECIMAL;
    public static final int KEY_F11          = org.lwjglx.input.Keyboard.KEY_F11;
    public static final int KEY_F12          = org.lwjglx.input.Keyboard.KEY_F12;
    public static final int KEY_F13          = org.lwjglx.input.Keyboard.KEY_F13;
    public static final int KEY_F14          = org.lwjglx.input.Keyboard.KEY_F14;
    public static final int KEY_F15          = org.lwjglx.input.Keyboard.KEY_F15;
    public static final int KEY_F16          = org.lwjglx.input.Keyboard.KEY_F16;
    public static final int KEY_F17          = org.lwjglx.input.Keyboard.KEY_F17;
    public static final int KEY_F18          = org.lwjglx.input.Keyboard.KEY_F18;
    public static final int KEY_KANA         = org.lwjglx.input.Keyboard.KEY_KANA;
    public static final int KEY_F19          = org.lwjglx.input.Keyboard.KEY_F19;
    public static final int KEY_CONVERT      = org.lwjglx.input.Keyboard.KEY_CONVERT;
    public static final int KEY_NOCONVERT    = org.lwjglx.input.Keyboard.KEY_NOCONVERT;
    public static final int KEY_YEN          = org.lwjglx.input.Keyboard.KEY_YEN;
    public static final int KEY_NUMPADEQUALS = org.lwjglx.input.Keyboard.KEY_NUMPADEQUALS;
    public static final int KEY_CIRCUMFLEX   = org.lwjglx.input.Keyboard.KEY_CIRCUMFLEX;
    public static final int KEY_AT           = org.lwjglx.input.Keyboard.KEY_AT;
    public static final int KEY_COLON        = org.lwjglx.input.Keyboard.KEY_COLON;
    public static final int KEY_UNDERLINE    = org.lwjglx.input.Keyboard.KEY_UNDERLINE;
    public static final int KEY_KANJI        = org.lwjglx.input.Keyboard.KEY_KANJI;
    public static final int KEY_STOP         = org.lwjglx.input.Keyboard.KEY_STOP;
    public static final int KEY_AX           = org.lwjglx.input.Keyboard.KEY_AX;
    public static final int KEY_UNLABELED    = org.lwjglx.input.Keyboard.KEY_UNLABELED;
    public static final int KEY_NUMPADENTER  = org.lwjglx.input.Keyboard.KEY_NUMPADENTER;
    public static final int KEY_RCONTROL     = org.lwjglx.input.Keyboard.KEY_RCONTROL;
    public static final int KEY_SECTION      = org.lwjglx.input.Keyboard.KEY_SECTION;
    public static final int KEY_NUMPADCOMMA  = org.lwjglx.input.Keyboard.KEY_NUMPADCOMMA;
    public static final int KEY_DIVIDE       = org.lwjglx.input.Keyboard.KEY_DIVIDE;
    public static final int KEY_SYSRQ        = org.lwjglx.input.Keyboard.KEY_SYSRQ;
    public static final int KEY_RMENU        = org.lwjglx.input.Keyboard.KEY_RMENU;
    public static final int KEY_FUNCTION     = org.lwjglx.input.Keyboard.KEY_FUNCTION;
    public static final int KEY_PAUSE        = org.lwjglx.input.Keyboard.KEY_PAUSE;
    public static final int KEY_HOME         = org.lwjglx.input.Keyboard.KEY_HOME;
    public static final int KEY_UP           = org.lwjglx.input.Keyboard.KEY_UP;
    public static final int KEY_PRIOR        = org.lwjglx.input.Keyboard.KEY_PRIOR;
    public static final int KEY_LEFT         = org.lwjglx.input.Keyboard.KEY_LEFT;
    public static final int KEY_RIGHT        = org.lwjglx.input.Keyboard.KEY_RIGHT;
    public static final int KEY_END          = org.lwjglx.input.Keyboard.KEY_END;
    public static final int KEY_DOWN         = org.lwjglx.input.Keyboard.KEY_DOWN;
    public static final int KEY_NEXT         = org.lwjglx.input.Keyboard.KEY_NEXT;
    public static final int KEY_INSERT       = org.lwjglx.input.Keyboard.KEY_INSERT;
    public static final int KEY_DELETE       = org.lwjglx.input.Keyboard.KEY_DELETE;
    public static final int KEY_CLEAR        = org.lwjglx.input.Keyboard.KEY_CLEAR;
    public static final int KEY_LMETA        = org.lwjglx.input.Keyboard.KEY_LMETA;
    /** @deprecated Use KEY_LMETA */
    public static final int KEY_LWIN         = org.lwjglx.input.Keyboard.KEY_LWIN;
    public static final int KEY_RMETA        = org.lwjglx.input.Keyboard.KEY_RMETA;
    /** @deprecated Use KEY_RMETA */
    public static final int KEY_RWIN         = org.lwjglx.input.Keyboard.KEY_RWIN;
    public static final int KEY_APPS         = org.lwjglx.input.Keyboard.KEY_APPS;
    public static final int KEY_POWER        = org.lwjglx.input.Keyboard.KEY_POWER;
    public static final int KEY_SLEEP        = org.lwjglx.input.Keyboard.KEY_SLEEP;

    public static final int KEYBOARD_SIZE    = org.lwjglx.input.Keyboard.KEYBOARD_SIZE;

    public static void create() {
        org.lwjglx.input.Keyboard.create();
    }

    public static void destroy() {
        org.lwjglx.input.Keyboard.destroy();
    }

    public static boolean next() {
        return org.lwjglx.input.Keyboard.next();
    }

    public static int getEventKey() {
        return org.lwjglx.input.Keyboard.getEventKey();
    }

    public static boolean getEventKeyState() {
        return org.lwjglx.input.Keyboard.getEventKeyState();
    }

    public static char getEventCharacter() {
        return org.lwjglx.input.Keyboard.getEventCharacter();
    }

    public static boolean isRepeatEvent() {
        return org.lwjglx.input.Keyboard.isRepeatEvent();
    }

    public static boolean areRepeatEventsEnabled() {
        return org.lwjglx.input.Keyboard.isRepeatEvent();
    }

    public static void enableRepeatEvents(boolean enable) {
        org.lwjglx.input.Keyboard.enableRepeatEvents(enable);
    }

    public static boolean isKeyDown(int key) {
        return org.lwjglx.input.Keyboard.isKeyDown(key);
    }

    public static boolean isCreated() {
        return org.lwjglx.input.Keyboard.isCreated();
    }

    public static String getKeyName(int key) {
        return org.lwjglx.input.Keyboard.getKeyName(key);
    }

    public static int getKeyIndex(String keyName) {
        return org.lwjglx.input.Keyboard.getKeyIndex(keyName);
    }

    public static int getKeyCount() {
        return org.lwjglx.input.Keyboard.getKeyCount();
    }

    public static ByteBuffer getKeyDownBuffer() {
        return org.lwjglx.input.Keyboard.keyDownBuffer;
    }

    /** No-op: event queue is driven by GLFW callbacks. */
    public static void poll() {
    }

    public static long getEventNanoseconds() {
        return System.nanoTime();
    }
}
