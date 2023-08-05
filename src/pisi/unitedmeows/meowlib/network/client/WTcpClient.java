package pisi.unitedmeows.meowlib.network.client;

import pisi.unitedmeows.meowlib.async.Async;
import pisi.unitedmeows.meowlib.async.Promise;
import pisi.unitedmeows.meowlib.clazz.event;
import pisi.unitedmeows.meowlib.network.IPAddress;
import pisi.unitedmeows.meowlib.network.NetworkConstants;
import pisi.unitedmeows.meowlib.network.client.events.DCDataReceived;
import pisi.unitedmeows.meowlib.thread.kThread;

import java.io.*;
import java.net.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import static pisi.unitedmeows.meowlib.async.Async.*;

public class WTcpClient {
    private final Socket socket;
    public event<DCDataReceived> dataReceivedEvent = new event<>();
    private final byte[] BUFFER = new byte[4096 * 2];
    private Thread receiveThread;
    private Thread writeThread;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;


    private final Queue<byte[]> writeQueue;

    private boolean keepAlive;
    private int keepAliveInterval = 20000;
    private Promise keepAlivePromise;

    public WTcpClient(/* proxy support ? */) {
        socket = new Socket();
        writeQueue = new ArrayDeque<>();
        try {
            socket.setTcpNoDelay(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public WTcpClient connect(IPAddress ipAddress, int port) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress.getAddress());
            final SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);

            socket.connect(socketAddress);
            socket.setTcpNoDelay(true);
        } catch (IOException e) {
            /* find a better way for exceptions */
        }


        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ignored) {
        }

        if (receiveThread != null) {
            try {
                receiveThread.stop();
            } catch (Exception ex) {
                //todo:
            }
        }
        if (keepAlivePromise != null) {
            keepAlivePromise.stop();
        }
        if (keepAlive) {
            keepAlivePromise = Async.async_loop(x -> {
                // send keepalive
                if (socket.isConnected()) {
                    send(NetworkConstants.KEEPALIVE_DATA);
                }
            }, keepAliveInterval);
        }

        if (writeThread != null) {
            writeThread.stop();
        }
        writeThread = new Thread(() -> {
            try {
                Thread.sleep(250);
            } catch (InterruptedException ignored) {
            }
            while (socket.isConnected()) {
                if (!writeQueue.isEmpty()) {
                    byte[] current = writeQueue.poll();
                    _send(current);
                    kThread.sleep(10);
                }
            }
        });


        receiveThread = new Thread(this::receive);
        receiveThread.start();
        writeThread.start();
        return this;
    }

    private synchronized void _send(byte[] data) {
        try {
            outputStream.write(data, 0, data.length);
            outputStream.flush();
        } catch (Exception ignored) {
        }
    }

    public void send(byte[] data) {
        writeQueue.add(data);
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    private void receive() {
        while (!socket.isClosed()) {
            try {
                int size = inputStream.read(BUFFER);
                byte[] data = Arrays.copyOf(BUFFER, size);
                // received
                async(u -> {
                    dataReceivedEvent.run((Object) data);
                });
            } catch (Exception ignored) {
            }
        }
    }

    public void close() {

        dataReceivedEvent.unbindAll();
        if (keepAlivePromise != null)
            keepAlivePromise.stop();


        try {
            if (writeThread != null) {
                writeThread.stop();
            }
        } catch (Exception ignored) {

        }

        try {
            if (receiveThread != null) {
                receiveThread.stop();
            }
        } catch (Exception ignored) {

        }
        writeQueue.clear();
        try {
            socket.close();
        } catch (IOException ignored) {

        }
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public WTcpClient setKeepAliveInterval(int keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
        return this;
    }

    public WTcpClient setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
        return this;
    }

    public Socket socket() {
        return socket;
    }
}
