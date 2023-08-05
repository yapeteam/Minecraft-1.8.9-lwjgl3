package pisi.unitedmeows.meowlib.network.server;

import pisi.unitedmeows.meowlib.clazz.event;
import pisi.unitedmeows.meowlib.network.server.events.DSDataReceived;
import pisi.unitedmeows.meowlib.network.server.events.DTCDataSendRequest;

import java.nio.channels.SocketChannel;

public class WCTcpTunnel {

    public event<DTCDataSendRequest> dataSendRequestEvent = new event<>();

    private SocketClient client1, client2;

    private WTcpServer server;

    private int receiveEventId;

    private byte serverId;

    public WCTcpTunnel(WTcpServer server, SocketClient client1, SocketClient client2) {
        this(server, client1.socketChannel(), client2.socketChannel());
    }

    public WCTcpTunnel(WTcpServer server, SocketChannel client1, SocketChannel client2) {
        if (!client1.isConnected() || !client2.isConnected()) {
            return;
        }
        this.server = server;
        this.serverId = SocketClient.sharedConnectedServer.put(server);
        this.client1 = new SocketClient(client1, serverId);
        this.client2 = new SocketClient(client2, serverId);

        receiveEventId = server.dataReceivedEvent.bind(new DSDataReceived() {
            @Override
            public void onDataReceived(SocketClient client, byte[] data) {
                if (client.socketChannel() == client1) {
                    DTCDataSendRequest.Args args = new DTCDataSendRequest.Args();
                    dataSendRequestEvent.run();
                    if (!args.canceled) {
                        client2().send(data);
                    }
                } else if (client.socketChannel() == client2) {
                    DTCDataSendRequest.Args args = new DTCDataSendRequest.Args();
                    dataSendRequestEvent.run();
                    if (!args.canceled) {
                       client1().send(data);
                    }
                }
            }
        });
    }

    public SocketClient client1() {
        return client1;
    }

    public SocketClient client2() {
        return client2;
    }

    public void closeTunnel() {
        server.dataReceivedEvent.unbind(receiveEventId);
    }

    public void closeTunnelAndConnections() {
        closeTunnel();
        server.stop();
    }
}
