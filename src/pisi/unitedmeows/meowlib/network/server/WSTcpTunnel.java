package pisi.unitedmeows.meowlib.network.server;

import pisi.unitedmeows.meowlib.clazz.event;
import pisi.unitedmeows.meowlib.network.client.WTcpClient;
import pisi.unitedmeows.meowlib.network.client.events.DCDataReceived;
import pisi.unitedmeows.meowlib.network.server.events.DTSDataSendRequest;

public class WSTcpTunnel {

    private WTcpClient client1;
    private WTcpClient client2;

    public event<DTSDataSendRequest> dataSendRequestEvent = new event<>();
    private int receive1Event, receive2Event;

    public WSTcpTunnel(WTcpClient server1, WTcpClient server2) {
        if (!server1.isConnected() || !server2.isConnected())
        {
            return;
        }

        this.client1 = server1;
        this.client2 = server2;

        receive1Event = server1.dataReceivedEvent.bind(new DCDataReceived() {
            @Override
            public void onDataReceived(byte[] data) {
                DTSDataSendRequest.Args args = new DTSDataSendRequest.Args();
                dataSendRequestEvent.run(server1, server2, data, args);
                if (!args.canceled) {
                    server2.send(data);
                }
            }
        });

        receive2Event = server2.dataReceivedEvent.bind(new DCDataReceived() {
            @Override
            public void onDataReceived(byte[] data) {
                DTSDataSendRequest.Args args = new DTSDataSendRequest.Args();
                dataSendRequestEvent.run(server2, server1, data, args);
                if (!args.canceled) {
                    server1.send(data);
                }
            }
        });
    }

    public void closeTunnel() {
        client1.dataReceivedEvent.unbind(receive1Event);
        client2.dataReceivedEvent.unbind(receive2Event);
    }

    public void closeTunnelAndConnections() {
        closeTunnel();
        client1.close();
        client2.close();
    }

    public WTcpClient server1() {
        return client1;
    }

    public WTcpClient server2() {
        return client2;
    }
}
