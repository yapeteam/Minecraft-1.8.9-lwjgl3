package pisi.unitedmeows.meowlib.network.server.events;

import pisi.unitedmeows.meowlib.clazz.delegate;
import pisi.unitedmeows.meowlib.network.server.SocketClient;


public interface DSDataReceived extends delegate {
    void onDataReceived(SocketClient client, byte[] data);
}
