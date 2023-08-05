package pisi.unitedmeows.meowlib.network.server.events;

import pisi.unitedmeows.meowlib.clazz.delegate;
import pisi.unitedmeows.meowlib.network.client.WTcpClient;

public interface DTSDataSendRequest extends delegate {
    void onDataSendRequest(WTcpClient from, WTcpClient to, byte[] data, Args args);

    public static class Args {
        public boolean canceled = false;
    }
}
