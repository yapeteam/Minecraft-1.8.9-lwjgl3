package pisi.unitedmeows.meowlib.network.server.events;

import pisi.unitedmeows.meowlib.clazz.delegate;
import pisi.unitedmeows.meowlib.network.server.SocketClient;

public interface DSClientQuit extends delegate {
    void onClientQuit(SocketClient client);
}
