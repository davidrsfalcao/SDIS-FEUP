import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class SReclaimProtocol implements Runnable {
    Peer peer;
    String[] protocol;

    public SReclaimProtocol(String[] protocol, Peer peer)  throws IOException, InterruptedException  {
        this.peer = peer;
        this.protocol = protocol;
    }

    public void run() {

    }
}