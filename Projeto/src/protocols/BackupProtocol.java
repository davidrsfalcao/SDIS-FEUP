package protocols;

import server.Peer;
import utils.Constants;
import utils.Header;
import utils.Utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class BackupProtocol implements Runnable {
    Peer peer;
    int replicationDegree;
    byte[] chunk;
    String fileId;

    String mdbAdress;
    int mdbPort;
    public MulticastSocket socket;
    public static InetAddress address;

    public static ConcurrentHashMap<String, Integer> requestsFileReplication;

    public BackupProtocol(String version, String senderId, String path, int replicationDegree, Peer peer)  throws IOException, InterruptedException  {
        this.peer = peer;
        this.replicationDegree = replicationDegree;

        //this.fileId = Utils.getFileId(new File(path));
        //peer.manageHashMaps(this.fileId);
        //requestsFileReplication.put(fileId, replicationDegree);


        //open mdbChannel//
        mdbAdress = peer.getMdbAddress();
        mdbPort = peer.getMdbPort();
        socket = new MulticastSocket(mdbPort);
        socket.setTimeToLive(1);
        address = InetAddress.getByName(mdbAdress);

        // TODO : Manager.backupFile(path, replicationDegree);
    }

    public void run() {
        byte[] body = readFile();
        byte[] chunk = createChunk(body);
        sendPutchunk(chunk);
    }

    private void error() {
        System.out.println("Error!!");
    }

    private void end() {
        System.out.println("Ended!!");
    }

    private byte[] readFile() {
        return null;
    }

    private void sendPutchunk(byte[] body) {
        int i = 0;
        int time = 1000;

        try {
            this.socket.joinGroup(this.address);

            while (i < 5) {
                DatagramPacket msgPacket = new DatagramPacket(this.chunk, this.chunk.length, this.address, this.mdbPort);
                this.socket.send(msgPacket);

                Thread.sleep(time);

                /*if (peer.verifyReplication()) {
                    break;
                }
                else {
                    i++;
                    time *= 2;
                }*/
            }
        }
        catch (IOException error) {
            error.printStackTrace();
        }
        catch (InterruptedException error) {
            System.out.println("Interrupted!");
        }

        if (i == 5)
            error();

        end();
    }

    private byte[] createChunk(byte[] body) {
        Header tempHeader = new Header(Constants.PUTCHUNK, "1.0", String.valueOf(peer.getServerID()), this.fileId, "1", String.valueOf(this.replicationDegree));
        String headerTemp = tempHeader.toString();
        byte[] header = headerTemp.getBytes();
        byte[] c = new byte[header.length + body.length];
        System.arraycopy(header, 0, c, 0, header.length);
        System.arraycopy(body, 0, c, header.length, body.length);

        return c;
    }
}
