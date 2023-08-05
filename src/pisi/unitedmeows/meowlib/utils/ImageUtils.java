package pisi.unitedmeows.meowlib.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    public static byte[] toByteArray(BufferedImage bi, ImageType type) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, type.type(), baos);
        } catch (IOException e) {
            return null;
        }
        byte[] bytes = baos.toByteArray();
        return bytes;

    }

    public static BufferedImage toBufferedImage(byte[] bytes) {
        try {
            InputStream is = new ByteArrayInputStream(bytes);
            BufferedImage bi = ImageIO.read(is);
            return bi;
        } catch (IOException ex) {
            return null;
        }
    }

    public enum ImageType {
        PNG("png"),
        JPG("jpg"),
        GIF("gif"); /* todo: add more */
        String type;
        ImageType(String type) {
            this.type = type;
        }

        public String type() {
            return type;
        }
    }
}
