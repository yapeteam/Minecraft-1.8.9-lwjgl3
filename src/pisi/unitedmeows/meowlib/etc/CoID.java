package pisi.unitedmeows.meowlib.etc;

import java.util.Random;
import java.util.regex.Pattern;

/** Complex IDentity */
public class CoID implements Comparable<CoID> {

    //SS$UDUD-LLD-SULUUULL-DD
    public static final String ALL_UPPERCASE = "ABCDEFGHIJKLMNOPRSTUVYZXQ";
    public static final String DIGITS = "0123456789";
    public static final String ALL_LOWERCASE = "abcdefghijklmnoprstuvyzxq";
    public static final String SPECIAL_CHARS = "COMPLEX2173";
    private static final Pattern coidPattern = Pattern.compile("[COMPLEX2173]{2}\\$[A-Z][0-9][A-Z][0-9]\\-[a-z]{2}[0-9]\\-[COMPLEX2173][A-Z][a-z][A-Z]{3}[a-z]{2}\\-[0-9]{2}");
    private static Random random = new Random();
    private static final int COID_LENGTH = 23;

    private final String value;

    public CoID(String val) {
        value = val;
    }



    @Override
    public String toString() {
        return value;
    }

    public static CoID generate() {
        //SS$UDUD-LLD-SULUUULL-DD
        StringBuilder builder = new StringBuilder();
        // SS
        builder.append(nextSpecial());
        builder.append(nextSpecial());
        builder.append('$');

        //UDUD
        builder.append(nextUpper());
        builder.append(nextDigit());
        builder.append(nextUpper());
        builder.append(nextDigit());
        builder.append('-');

        //LLD
        builder.append(nextLower());
        builder.append(nextLower());
        builder.append(nextDigit());
        builder.append('-');

        //SULUUULL
        builder.append(nextSpecial()); // S
        builder.append(nextUpper()); // U
        builder.append(nextLower()); // L
        builder.append(nextUpper()); // U
        builder.append(nextUpper()); // U
        builder.append(nextUpper()); // U
        builder.append(nextLower()); // L
        builder.append(nextLower()); // L
        builder.append('-');
        //DD
        builder.append(nextDigit());
        builder.append(nextDigit());
        return new CoID(builder.toString());

    }

    private static char nextUpper() {
        return ALL_UPPERCASE.charAt(random.nextInt(ALL_LOWERCASE.length()));
    }

    private static char nextLower() {
        return ALL_LOWERCASE.charAt(random.nextInt(ALL_LOWERCASE.length()));
    }

    private static char nextSpecial() {
        return SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length()));
    }

    private static char nextDigit() {
        return DIGITS.charAt(random.nextInt(DIGITS.length()));
    }


    //TODO: Check for pattern
    @Deprecated
    public static boolean isLegal(String coid) {
        if (coid.length() != 23) {
            return false;
        }
        return coidPattern.matcher(coid).matches();
    }

    public static boolean isLegal(CoID coID) {
        return isLegal(coID.toString());
    }



    public static void setRandom(Random random) {
        CoID.random = random;
    }

    @Override
    public int compareTo(CoID o) {
        return toString().equals(o.toString()) ? 1 : 0;
    }

    public boolean same(CoID o2) {
        return toString().equals(o2.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoID coID = (CoID) o;

        return value != null ? value.equals(coID.value) : coID.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
