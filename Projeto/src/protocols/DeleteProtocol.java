package protocols;

import server.Peer;

import java.io.IOException;


public class DeleteProtocol implements Runnable {
    Peer peer;

    public DeleteProtocol(String version, String senderId, String path, Peer peer)  throws IOException, InterruptedException  {
        this.peer = peer;
    }

    public void run() {

    }

}
