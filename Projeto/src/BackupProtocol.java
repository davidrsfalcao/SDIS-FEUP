import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class BackupProtocol implements Runnable {
    Peer peer;
    String[] protocol;

    public BackupProtocol(String[] protocol, Peer peer)  throws IOException, InterruptedException  {
        this.peer = peer;
        this.protocol = protocol;
    }

    public void run() {

    }
}