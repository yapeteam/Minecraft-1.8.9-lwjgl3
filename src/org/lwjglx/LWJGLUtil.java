package org.lwjglx;


import org.lwjgl.opengl.GL;

import java.security.AccessController;
import java.security.PrivilegedAction;

import static org.lwjgl.opengl.ARBImaging.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class LWJGLUtil {

    public static final boolean DEBUG = getPrivilegedBoolean("org.lwjgl.util.Debug");

    public static boolean getPrivilegedBoolean(final String property_name) {
        return AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
            public Boolean run() {
                return Boolean.getBoolean(property_name);
            }
        });
    }
    public static String translateGLErrorString(int error_code) {
        switch (error_code) {
            case GL_NO_ERROR:
                return "No error";
            case GL_INVALID_ENUM:
                return "Invalid enum";
            case GL_INVALID_VALUE:
                return "Invalid value";
            case GL_INVALID_OPERATION:
                return "Invalid operation";
            case GL_STACK_OVERFLOW:
                return "Stack overflow";
            case GL_STACK_UNDERFLOW:
                return "Stack underflow";
            case GL_OUT_OF_MEMORY:
                return "Out of memory";
            case GL_TABLE_TOO_LARGE:
                return "Table too large";
            case GL_INVALID_FRAMEBUFFER_OPERATION:
                return "Invalid framebuffer operation";
            default:
                return null;
        }
    }
    public static void log(String log){
        System.out.println(log);
    }

}
