package protocols;

import server.Peer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class ReclaimProtocol implements Runnable {
    Peer peer;

    public ReclaimProtocol(String version, String senderId, int space, Peer peer)  throws IOException, InterruptedException  {
        this.peer = peer;

        /*MAXIMUM_SPACE = space;
        freeUpSpace();*/
    }

    public void run() {

    }
}
