package pisi.unitedmeows.meowlib.filesystem;

import pisi.unitedmeows.meowlib.etc.Tuple;
import pisi.unitedmeows.meowlib.predefined.STRING;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * life is too short to use
 * 'new File(fileName)'
 */
public class kFile {

    public static boolean exists(String fileName) {
        return get(fileName).isFile();
    }

    public static Tuple<Boolean, File> exists_r(String fileName) {
        File file = new File(fileName);
        return new Tuple<>(file.isFile(), file);
    }

    public static List<String> readAllLines(String fileName) {
        return readAllLines(get(fileName));
    }

    public static List<String> readAllLines(File file) {
        List<String> readContent = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            String str;
            while ((str = in.readLine()) != null)
                readContent.add(str);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readContent;
    }

    public static String read(String fileName) {
        return read(get(fileName));
    }

    public static String read(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));

        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(STRING.L_SEPARATOR);
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
        } catch (IOException e) {
            return STRING.EMPTY;
        }
    }

    /* copied from somewhere idk */
    /**
     * faster read for big files
     * @param f
     * @return
     */
    public static String read_f(File f) {
        StringBuilder text = new StringBuilder();
        int read, N = 1024 * 1024;
        char[] buffer = new char[N];

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));

            while(true) {
                read = br.read(buffer, 0, N);
                text.append(new String(buffer, 0, read));

                if(read < N) {
                    break;
                }
            }
            br.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return text.toString();
    }

    public static boolean delete(String fileName) {
        return get(fileName).delete();
    }

    public static boolean create(String fileName) {
        try {
            return get(fileName).createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    public static String extension(File file) {
        String extension = "";
        
        try {
            if (file != null && file.isFile()) {
                String name = file.getName();
                extension = name.substring(name.lastIndexOf(STRING.DOT));
            }
        } catch (Exception e) {
            extension = STRING.EMPTY;
        }

        return extension;
    }

    public static String extension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(STRING.DOT));
    }

    public static File get(String fileName) {
        return new File(fileName);
    }

}
