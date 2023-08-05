package pisi.unitedmeows.meowlib.encryption.memory;

/**
 * memory safe strings
 */
public class MString implements Comparable<MString> {

    private char[] value;

    public MString(String input) {

        final String encrypted = input;

        value = new char[encrypted.length()];
        int i = 0;
        for (char c : encrypted.toCharArray()) {
            value[i] = c;
            i++;
        }
    }


    @Override
    public String toString() {
        // decrypt
        return value.toString();
    }

    @Override
    public int compareTo(MString o) {
        return 0;
    }
}
