package protocols;

import server.Peer;
import utils.Header;
import utils.Utils;
import static utils.Constants.MAXCHUNKSIZE;
import static utils.Constants.PUTCHUNK;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.io.FileInputStream;

public class BackupProtocol implements Runnable {
    Peer peer;
    int replicationDegree;
    String fileId;

    private FileInputStream file;

    String mdbAdress;
    int mdbPort;
    public MulticastSocket socket;
    public static InetAddress address;

    public static ConcurrentHashMap<String, Integer> requestsFileReplication = new ConcurrentHashMap<>();

    public BackupProtocol(String version, int senderId, String path, int replicationDegree, Peer peer) {
        this.peer = peer;
        this.replicationDegree = 1; //TODO
        File temp = new File(path);

        this.fileId = Utils.getFileId(temp);
        peer.manageHashMaps(this.fileId);
        //requestsFileReplication.put(fileId, replicationDegree);

        //open mdbChannel//
        try {
            this.file = new FileInputStream(path);

            mdbAdress = peer.getMdbAddress();
            mdbPort = peer.getMdbPort();
            socket = new MulticastSocket(mdbPort);
            socket.setTimeToLive(1);
            address = InetAddress.getByName(mdbAdress);
        }
        catch (IOException error) {
            System.err.println("BackupProtocol exception: " + error.toString());
        }

    }

    public void run() {
        try {
            byte[] body = new byte[MAXCHUNKSIZE];
            int numberBytes = 0;
            int chunkNumber = 0;
            while ( ( numberBytes = this.file.read(body, 0, MAXCHUNKSIZE) ) != -1) {
                byte[] chunk = createChunk(body, chunkNumber);
                System.out.println(chunk.length);
                sendPutchunk(chunk);

                chunkNumber++;
                body = new byte[MAXCHUNKSIZE];
            }
        }
        catch (IOException error) {
            error.printStackTrace();
        }
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

    private void sendPutchunk(byte[] chunk) {
        int i = 0;
        int time = 1000;

        try {
            this.socket.joinGroup(this.address);

            while (i < 5) {
                DatagramPacket msgPacket = new DatagramPacket(chunk, chunk.length, this.address, this.mdbPort);
                this.socket.send(msgPacket);

                Thread.sleep(2000);

                break;
                /*if (peer.verifyReplication()) {
                    break;
                }
                else {
                    i++;
                    time += 1000;
                }*/
            }

            this.socket.leaveGroup(this.address);
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

    private byte[] createChunk(byte[] body, int chunkNumber) {
        Header tempHeader = new Header(PUTCHUNK, "1.0", String.valueOf(peer.getServerID()), this.fileId, String.valueOf(chunkNumber), String.valueOf(this.replicationDegree));
        String headerTemp = tempHeader.toString();

        System.out.println(headerTemp);

        byte[] header = headerTemp.getBytes();
        byte[] c = new byte[header.length + body.length];
        System.arraycopy(header, 0, c, 0, header.length);
        System.arraycopy(body, 0, c, header.length, body.length);

        return c;
    }
}