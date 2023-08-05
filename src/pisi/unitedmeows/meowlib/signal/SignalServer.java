package pisi.unitedmeows.meowlib.signal;

import pisi.unitedmeows.meowlib.etc.CoID;
import pisi.unitedmeows.meowlib.network.IPAddress;
import pisi.unitedmeows.meowlib.network.server.SocketClient;
import pisi.unitedmeows.meowlib.network.server.WTcpServer;
import pisi.unitedmeows.meowlib.network.server.events.DSDataReceived;
import pisi.unitedmeows.meowlib.random.WRandom;
import stelix.xfile.SxfDataBlock;
import stelix.xfile.SxfFile;
import stelix.xfile.writer.SxfWriter;

import java.util.Arrays;

public class SignalServer {

    private final WTcpServer tcpServer;

    public SignalServer(String appName, double appVersion, CoID appKey) {
        SxfFile appFile = new SxfFile();
        SxfDataBlock app = new SxfDataBlock();
        app.putVar("appName", appName);
        app.putVar("appVersion", appVersion);
        app.putVar("appKey", appKey.toString());
        appFile.put("app", app);

        SxfWriter writer = new SxfWriter();
        writer.setWriteType(SxfWriter.WriteType.INLINE);
        byte[] appData = writer.writeToString(appFile).getBytes();


        tcpServer = new WTcpServer(IPAddress.LOOPBACK, WRandom.BASIC.nextInRange(2950, 3100));

        tcpServer.dataReceivedEvent.bind(new DSDataReceived() {
            @Override
            public void onDataReceived(SocketClient client, byte[] data) {
                if (Arrays.equals(data, Signal.GET_INFO)) {
                    ;client.send(appData);
                }
            }
        });
    }

    public void listen() {
        tcpServer.listen();
    }


}
