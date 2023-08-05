package pisi.unitedmeows.meowlib.filesystem;

import java.io.File;
import java.util.List;

public class kDirectory {


    public static File[] listFiles(String dirName) {
        return get(dirName).listFiles();
    }

    public static boolean delete(String dirName) {
        return get(dirName).delete();
    }

    public static boolean exists(String dirName) {
        return get(dirName).isDirectory();
    }


    public static File get(String dirName) {
        return new File(dirName);
    }
}
