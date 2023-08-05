package pisi.unitedmeows.meowlib.network.client.events;

import pisi.unitedmeows.meowlib.clazz.delegate;

public interface DCDataReceived extends delegate {
    public void onDataReceived(byte[] data);
}
