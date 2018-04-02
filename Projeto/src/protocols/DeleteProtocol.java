package protocols;

import server.Peer;
import utils.Header;
import utils.Utils;

import static utils.Constants.DELETE;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class DeleteProtocol implements Runnable {
    private String path;
    private Peer peer;
    private int senderId;
    private String version;
    public MulticastSocket socket;
    public String fileId;

    public DeleteProtocol(String version, int senderId, String path, Peer peer) {
        this.version = version;
        this.path = path;
        this.senderId = senderId;
        this.peer = peer;

        File temp = new File(path);
        this.fileId = Utils.getFileId(temp);
        peer.manageHashMaps(this.fileId);
        //requestsFileReplication.put(fileId, replicationDegree);

        //open mdbChannel//
      /*  try {
            file = new FileInputStream(path);

            mdbAdress = peer.getMdbAddress();
            mdbPort = peer.getMdbPort();
            socket = new MulticastSocket(mdbPort);
            socket.setTimeToLive(1);
            address = InetAddress.getByName(mdbAdress);
        }
        catch (IOException error) {
            System.err.println("BackupProtocol exception: " + error.toString());
        }*/
    }

    public void run() {
     /*   File file = new File(path);
        String fileId = Utils.getFileId(file);

        String serverID = peer.getServerID() + "";

        Header header = new Header(DELETE, "1.0", serverID, fileId, null, null);
        new Thread(header).start();


        if(file.delete()){
            System.out.println(file.getName() + " is deleted!");
        }else{
            System.out.println("Delete operation failed.");
        }
        Peer.getInstance().getStorage().getChunksBackedUp().remove(fileId);*/
    }
}
