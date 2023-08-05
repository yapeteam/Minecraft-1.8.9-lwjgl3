package org.lwjglx.openal;

import org.lwjglx.BufferUtils;
import org.lwjglx.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.IntBuffer;

public class xAL {

    static ALCdevice alcDevice;
    static ALCcontext alcContext;

    private static boolean created = false;

    static {
        //Sys.initialize(); // init using dummy sys method
    }

    public static void create() throws LWJGLException {
        IntBuffer attribs = BufferUtils.createIntBuffer(16);

        attribs.put(ALC10.ALC_FREQUENCY);
        attribs.put(44100);

        attribs.put(ALC10.ALC_REFRESH);
        attribs.put(60);

        attribs.put(ALC10.ALC_SYNC);
        attribs.put(ALC10.ALC_FALSE);

        attribs.put(0);
        attribs.flip();

        String defaultDevice = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);

        long deviceHandle = ALC10.alcOpenDevice(defaultDevice);

        alcDevice = new ALCdevice(deviceHandle);

        final ALCCapabilities deviceCaps = ALC.createCapabilities(deviceHandle);

        long contextHandle = ALC10.alcCreateContext(xAL.getDevice().device, attribs);
        alcContext = new ALCcontext(contextHandle);
        ALC10.alcMakeContextCurrent(contextHandle);
        AL.createCapabilities(deviceCaps);

        created = true;
    }

    public static boolean isCreated() {
        return created;
    }

    public static void destroy() {
        ALC10.alcDestroyContext(alcContext.context);
        ALC10.alcCloseDevice(alcDevice.device);
        alcContext = null;
        alcDevice = null;
        created = false;
    }

    public static ALCdevice getDevice() {
        return alcDevice;
    }
}
