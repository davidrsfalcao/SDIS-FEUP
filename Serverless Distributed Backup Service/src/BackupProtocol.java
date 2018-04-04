import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BackupProtocol implements Runnable {
    Peer peer;
    int replicationDegree;
    String fileId;

    private FileInputStream file;
    private File f;

    String mdbAdress;
    int mdbPort;
    public MulticastSocket socket;
    public static InetAddress address;

    String mcAdress;
    int mcPort;
    public MulticastSocket socketMc;
    public static InetAddress addressMc;

    public static ConcurrentHashMap<String, Integer> requestsFileReplication = new ConcurrentHashMap<>();

    public BackupProtocol(String version, String senderId, String path, int replicationDegree, Peer peer) {
        this.peer = peer;
        this.replicationDegree = 1; //TODO
        f = new File(path);

        this.fileId = Utils.getFileId(f);
        peer.manageHashMaps(this.fileId);
        //requestsFileReplication.put(fileId, replicationDegree);

        try {
            this.file = new FileInputStream(path);
        }
        catch (FileNotFoundException error) {
            System.err.println("File not found!" + error.toString());
        }
    }

    public void run() {
        try {
            byte[] bodytemp = new byte[Constants.MAXCHUNKSIZE];
            int numberBytes = 0;
            int chunkNumber = 0;
            while ( (numberBytes = this.file.read(bodytemp, 0, Constants.MAXCHUNKSIZE)) != -1 ) {
                System.out.println(numberBytes);
                byte[] body = new byte[numberBytes];
                System.arraycopy(bodytemp, 0, body, 0, numberBytes);
                byte[] chunk = createChunk(body, chunkNumber);
                System.out.println(chunk.length);
                sendPutchunk(chunk);
                chunkNumber++;
                bodytemp = new byte[Constants.MAXCHUNKSIZE];
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

    private void sendPutchunk(byte[] chunk) {
        int i = 0;
        int time = 1000;

        try {
            while (i < 5) {
                DatagramPacket msgPacket = new DatagramPacket(chunk, chunk.length, this.peer.getInetAddMdb(), this.peer.getMdbPort());
                this.peer.getMdbSocket().send(msgPacket);

                Thread.sleep(1000);

                break;
                /*if (peer.verifyReplication()) {
                    break;
                }
                else {
                    i++;
                    time += 1000;
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

    private byte[] createChunk(byte[] body, int chunkNumber) {
        Header tempHeader = new Header(Constants.PUTCHUNK, "1.0", String.valueOf(peer.getServerID()), this.fileId, String.valueOf(chunkNumber), String.valueOf(this.replicationDegree));
        String headerTemp = tempHeader.toString();

        System.out.println(headerTemp);

        byte[] header = headerTemp.getBytes();
        byte[] c = new byte[header.length + body.length];
        System.arraycopy(header, 0, c, 0, header.length);
        System.arraycopy(body, 0, c, header.length, body.length);

        return c;
    }
}
