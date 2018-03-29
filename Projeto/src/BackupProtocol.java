import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class BackupProtocol implements Runnable {
    Peer peer;
    int replicationDegree;

    public BackupProtocol(String version, String senderId, String path, int replicationDegree, Peer peer)  throws IOException, InterruptedException  {
        this.peer = peer;
        this.replicationDegree = replicationDegree;

        /*String fileId = Utils.getFileId(new File(path));
        fileIds.put(path, fileId);
        requestsFileReplication.put(fileId, replicationDegree);*/

        // TODO : Manager.backupFile(path, replicationDegree);
    }

    public void run() {

    }
}
