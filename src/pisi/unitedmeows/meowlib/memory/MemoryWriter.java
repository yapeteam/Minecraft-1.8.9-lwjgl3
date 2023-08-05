package pisi.unitedmeows.meowlib.memory;

import pisi.unitedmeows.meowlib.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.*;

public class MemoryWriter extends DataOutputStream {

    private ByteArrayOutputStream outputStream;

    
    public MemoryWriter() {
        super(null);
        outputStream = new ByteArrayOutputStream();
        out = outputStream;
    }

    public byte[] getBytes() {
        return outputStream.toByteArray();
    }

    public void writeImage(BufferedImage image, ImageUtils.ImageType type) {
        byte[] data = ImageUtils.toByteArray(image, type);
        try {
            write(data.length);

            write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
