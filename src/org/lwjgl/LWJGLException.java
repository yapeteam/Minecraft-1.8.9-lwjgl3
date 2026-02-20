package org.lwjgl;

public class LWJGLException extends Exception {
    public LWJGLException() { super(); }
    public LWJGLException(String message) { super(message); }
    public LWJGLException(String message, Throwable cause) { super(message, cause); }
}
