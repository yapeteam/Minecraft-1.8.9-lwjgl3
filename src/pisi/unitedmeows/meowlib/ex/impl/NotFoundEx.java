package pisi.unitedmeows.meowlib.ex.impl;

import pisi.unitedmeows.meowlib.ex.Ex;

public class NotFoundEx extends Ex
{

    private String message;
    public NotFoundEx(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
