package pisi.unitedmeows.meowlib.predefined;

import java.io.File;

public class STRING {

    public static final String EMPTY = "";
    public static final String DOT = ".";
    public static final String L_SEPARATOR = System.lineSeparator();

    /* add your statics methods related to string here */
    public static String string_reverse(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}
