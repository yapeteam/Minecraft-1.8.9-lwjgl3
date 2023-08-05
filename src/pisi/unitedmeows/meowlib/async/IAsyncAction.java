package pisi.unitedmeows.meowlib.async;

import pisi.unitedmeows.meowlib.etc.CoID;

@FunctionalInterface
public interface IAsyncAction {
    void start(CoID uuid);
}
