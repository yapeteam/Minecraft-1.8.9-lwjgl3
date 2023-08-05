package pisi.unitedmeows.meowlib.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPAddress {
    public static IPAddress LOOPBACK    = new IPAddress("127.0.0.1");
    public static IPAddress ANY         = new IPAddress("0.0.0.0");

    private String address;

    IPAddress(String _address) {
        address = _address;
    }


    public String getAddress() {
        return address;
    }

    public static IPAddress from(String address) {
        return new IPAddress(address);
    }

    public static IPAddress parse(String address) {
        try {
            return new IPAddress(InetAddress.getByName(address).getHostAddress());
        } catch (UnknownHostException e) {
            return new IPAddress(address);
        }
    }

}
