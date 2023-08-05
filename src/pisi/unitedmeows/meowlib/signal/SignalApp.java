package pisi.unitedmeows.meowlib.signal;

import pisi.unitedmeows.meowlib.etc.CoID;

public class SignalApp {

    private String appName;
    private double appVersion;
    private CoID appKey;

    public SignalApp(String appName, double appVersion, CoID appKey)
    {
        this.appName = appName;
        this.appVersion = appVersion;
        this.appKey = appKey;
    }

    public CoID appKey() {
        return appKey;
    }

    public double appVersion() {
        return appVersion;
    }

    public String appName() {
        return appName;
    }
}
