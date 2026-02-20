package org.lwjgl.opengl;

public class ContextAttribs {

    private int versionMajor, versionMinor;
    private boolean profileCore, debug;

    public ContextAttribs(int versionMajor, int versionMinor) {
        this.versionMajor = versionMajor;
        this.versionMinor = versionMinor;
    }

    public ContextAttribs() {
        this(0, 0);
    }

    public ContextAttribs withProfileCore(boolean profileCore) {
        this.profileCore = profileCore;
        return this;
    }

    public ContextAttribs withDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public int getVersionMajor() {
        return versionMajor;
    }

    public int getVersionMinor() {
        return versionMinor;
    }

    public boolean isProfileCore() {
        return profileCore;
    }

    public boolean isDebug() {
        return debug;
    }

    /** Converts to the lwjglx equivalent for internal use by Display. */
    org.lwjglx.opengl.ContextAttribs toLwjglx() {
        return new org.lwjglx.opengl.ContextAttribs(versionMajor, versionMinor)
                .withProfileCore(profileCore)
                .withDebug(debug);
    }
}
