package protocols;

import server.Peer;

import java.io.IOException;


public class RestoreProtocol implements Runnable {
    Peer peer;

    public RestoreProtocol(String version, String senderId, String path, Peer peer)  throws IOException, InterruptedException  {
        this.peer = peer;
    }

    public void run() {

    }
}
