package pisi.unitedmeows.meowlib.signal;

import pisi.unitedmeows.meowlib.network.IPAddress;
import pisi.unitedmeows.meowlib.thread.kThread;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static pisi.unitedmeows.meowlib.async.Async.async;

public class Signal {


    public static final byte[] GET_INFO = ";SIGNAL->GET_INFO".getBytes();

    /*TODO: USE STELIX TO SEND DATA */
    /* SXF 2.0 */

    /* 2950 - 3100 */


    public static List<Integer> discover_ports(IPAddress address) {
        List<Integer> ports = new ArrayList<>();

        for (int port = 2950; port < 3100; port++) {
            if (available(port)) {
                ports.add(port);
            }
        }
        return ports;
    }

    @Deprecated
    public static List<Integer> discover_ports_fast(IPAddress address) {
        final List<Integer> ports = new ArrayList<>();
        for (int port = 2950; port < 3100; port++) {
            int finalPort = port;;
            async(u-> {

                if (available(finalPort)) {
                    ports.add(finalPort);
                }
            });
        }

        kThread.sleep(1000);
        return ports;
    }

    private static boolean available(int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", port));
            boolean result = socket.isConnected();
            socket.close();
            return result;
        } catch (Exception ex) {
            return false;
        }
    }


}
