package pisi.unitedmeows.meowlib.async;

public class Promise {
    private boolean valid = true;

    public void stop() {
        valid = false;
    }

    public void start() {
        valid = true;
    }

    public boolean isValid() {
        return valid;
    }
}
