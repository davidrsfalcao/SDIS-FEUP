import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class RestoreProtocol implements Runnable {
    Peer peer;

    public RestoreProtocol(String version, String senderId, String path, Peer peer)  throws IOException, InterruptedException  {
        this.peer = peer;
    }

    public void run() {

    }
}
