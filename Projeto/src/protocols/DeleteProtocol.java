package protocols;

import server.Peer;
import utils.Header;
import utils.Manager;
import utils.Utils;

import static utils.Constants.DELETE;
import static utils.Constants.MAXCHUNKSIZE;
import static utils.Constants.PUTCHUNK;

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
    private MulticastSocket socket;
    private String fileId;
    private String mcAdress;
    private int mcPort;
    public static InetAddress address;


    public DeleteProtocol(String version, int senderId, String path, Peer peer) {
        this.version = version;
        this.path = path;
        this.senderId = ""+senderId;
        this.peer = peer;



        File file = new File(path);

        fileId = Utils.getFileId(file);

        try {
            mcAdress = peer.getMcAddress();
            mcPort = peer.getMcPort();
            socket = new MulticastSocket(mcPort);
            socket.setTimeToLive(1);
            address = InetAddress.getByName(mcAdress);
        }
        catch (IOException error) {
            System.err.println("DeleteProtocol exception: " + error.toString());
        }

    }

    public void run() {

        Header tempHeader = new Header(DELETE, version, senderId, fileId, null, null);
        String headerTemp = tempHeader.toString();

        byte[] header = headerTemp.getBytes();

        int i = 0;
        int time = 1000;

        try {
            this.socket.joinGroup(this.address);

            DatagramPacket msgPacket = new DatagramPacket(header, header.length, address, mcPort);
            socket.send(msgPacket);

            Thread.sleep(2000);


            this.socket.leaveGroup(this.address);
        }
        catch (IOException error) {
            error.printStackTrace();
        }
        catch (InterruptedException error) {
            System.out.println("Interrupted!");
        }




    }
}
