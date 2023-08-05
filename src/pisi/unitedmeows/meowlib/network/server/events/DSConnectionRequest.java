package pisi.unitedmeows.meowlib.network.server.events;

import pisi.unitedmeows.meowlib.clazz.delegate;
import pisi.unitedmeows.meowlib.network.server.SocketClient;

public interface DSConnectionRequest extends delegate {

    public void onClientConnecting(SocketClient client);
}
