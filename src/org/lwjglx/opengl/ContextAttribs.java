package org.lwjglx.opengl;

public class ContextAttribs {

    private int version_major, version_minor;
    private boolean profileCore, debug;

    public ContextAttribs(int version_major, int version_minor){
        this.version_major = version_major;
        this.version_minor = version_minor;
    }
    public ContextAttribs(){
        this.version_major = 0;
        this.version_minor = 0;
    }

    public ContextAttribs withProfileCore(boolean profileCore){
        this.profileCore = profileCore;
        return this;
    }

    public int getVersion_major() {
        return version_major;
    }

    public int getVersion_minor() {
        return version_minor;
    }

    public boolean isProfileCore() {
        return profileCore;
    }

    public ContextAttribs withDebug(boolean b) {
        this.debug = debug;
        return this;
    }
}
