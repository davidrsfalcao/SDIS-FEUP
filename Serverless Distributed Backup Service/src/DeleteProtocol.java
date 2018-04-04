import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class DeleteProtocol implements Runnable {
    private String path;
    private Peer peer;
    private String senderId;
    private String version;
    private String fileId;


    public DeleteProtocol(String version, int senderId, String path, Peer peer) {
        this.version = version;
        this.path = path;
        this.senderId = ""+senderId;
        this.peer = peer;



        File file = new File(path);

        fileId = Utils.getFileId(file);
    }

    public void run() {

        Header tempHeader = new Header(Constants.DELETE, version, senderId, fileId, null, null);
        String headerTemp = tempHeader.toString();

        byte[] header = headerTemp.getBytes();

        int i = 0;
        int time = 1000;

        System.out.println("Delete half way");

        try {
            DatagramPacket msgPacket = new DatagramPacket(header, header.length, this.peer.getInetAddMc(), this.peer.getMcPort() );
            this.peer.getMdbSocket().send(msgPacket);
        }
        catch (IOException error) {
            error.printStackTrace();
        }
    }
}
