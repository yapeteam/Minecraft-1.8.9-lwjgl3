package pisi.unitedmeows.meowlib.signal;

import pisi.unitedmeows.meowlib.etc.CoID;
import pisi.unitedmeows.meowlib.network.IPAddress;
import pisi.unitedmeows.meowlib.network.server.SocketClient;
import pisi.unitedmeows.meowlib.network.server.WTcpServer;
import pisi.unitedmeows.meowlib.network.server.events.DSDataReceived;
import pisi.unitedmeows.meowlib.random.WRandom;
import stelix.xfile.SxfBlockBuilder;
import stelix.xfile.WriteStyle;

import java.util.Arrays;

public class SignalServer {

    private final WTcpServer tcpServer;

    public SignalServer(String appName, double appVersion, CoID appKey) {
        SxfBlockBuilder appBlock = SxfBlockBuilder.create();
        appBlock.variable("appName", appName);
        appBlock.variable("appVersion", appVersion);
        appBlock.variable("appKey", appKey.toString());

        SxfBlockBuilder root = SxfBlockBuilder.create().setStyle(WriteStyle.INLINE);
        root.variable("app", appBlock);

        byte[] appData = root.toString().getBytes();


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
