package pisi.unitedmeows.meowlib.etc;

import java.util.UUID;

@FunctionalInterface
public interface IAction {
    void run() throws Exception;
}
