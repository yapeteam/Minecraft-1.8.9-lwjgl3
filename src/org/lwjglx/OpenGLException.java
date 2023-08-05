package org.lwjglx;

public class OpenGLException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Plain c'tor
     */
    public OpenGLException() {
        super();
    }

    /**
     * Creates a new OpenGLException
     *
     * @param msg
     *            String identifier for exception
     */
    public OpenGLException(String msg) {
        super(msg);
    }

    /**
     * @param message
     * @param cause
     */
    public OpenGLException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public OpenGLException(Throwable cause) {
        super(cause);
    }

}
