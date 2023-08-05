package pisi.unitedmeows.meowlib.thread;

import pisi.unitedmeows.meowlib.etc.IState;

public class kThread {

    public static boolean sleep(long millis) {
        try {
            Thread.sleep(millis);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public static void hang(IState state) {
        while (state.get()) {
            kThread.sleep(100);
        }
    }
}
