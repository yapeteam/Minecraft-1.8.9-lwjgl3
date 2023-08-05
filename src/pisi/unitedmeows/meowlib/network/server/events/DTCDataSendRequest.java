package pisi.unitedmeows.meowlib.network.server.events;

import pisi.unitedmeows.meowlib.clazz.delegate;

public interface DTCDataSendRequest extends delegate {
    void onDataSendRequest();

    public static class Args {
        public boolean canceled = false;
    }
}
