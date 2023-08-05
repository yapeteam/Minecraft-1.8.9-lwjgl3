package pisi.unitedmeows.meowlib.network.packet;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;

public interface IPacket {


    default void readPacket() {
        
    }
}
