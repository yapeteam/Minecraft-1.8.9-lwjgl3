package pisi.unitedmeows.meowlib;

import pisi.unitedmeows.meowlib.async.BasicTaskPool;
import pisi.unitedmeows.meowlib.async.ITaskPool;
import pisi.unitedmeows.meowlib.etc.IAction;
import pisi.unitedmeows.meowlib.etc.MLibSetting;
import pisi.unitedmeows.meowlib.etc.MLibSettings;
import pisi.unitedmeows.meowlib.ex.Ex;
import pisi.unitedmeows.meowlib.ex.ExceptionManager;
import pisi.unitedmeows.meowlib.variables.ubyte;
import pisi.unitedmeows.meowlib.variables.uint;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("unused")
public class MeowLib {

    /* change this to meowlib map */
    private static final HashMap<MLibSettings, MLibSetting<Serializable>> SETTINGS;

    private static ITaskPool taskPool;

    static {
        SETTINGS = new HashMap<>();
        taskPool = new BasicTaskPool();
        setup();
    }

    private static void setup() {
        for (MLibSettings setting : MLibSettings.values()) {
            SETTINGS.put(setting, new MLibSetting<>(setting, (Serializable) setting.getValue()));
        }
        taskPool.setup();
    }
    /* change this method name */
    public static HashMap<MLibSettings, MLibSetting<Serializable>> mLibSettings() {
        return SETTINGS;
    }


    public static ubyte ubyte(byte value) {
        return new ubyte(value);
    }

    public static ubyte ubyte(int value) {
        return new ubyte(ubyte.convert(value));
    }

    public static uint uint(int value) {
        return new uint(value);
    }

    public static uint uint(long value) {
        return new uint(uint.convert(value));
    }

    public static <X extends Ex> void throwEx(X ex) {
        ExceptionManager.throwEx(ex);
    }

    public static <X extends Ex> X lastError() {
        return ExceptionManager.lastError();
    }

    public static void useTaskPool(ITaskPool newPool) {
        if (taskPool != null) {
            taskPool.close();
        }
        taskPool = newPool;
        taskPool.setup();
    }

    public static ITaskPool getTaskPool() {
        return taskPool;
    }

    public static Exception run(IAction action) {
        try {
            action.run();
            return null;
        } catch (Exception ex) {
            return ex;
        }
    }
}
