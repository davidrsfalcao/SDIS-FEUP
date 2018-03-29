import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class Peer {

    private int serverID;
    private static Peer peer;
    private static Thread backup;
    private static Thread delete;
    private static Thread restore;
    private static Thread spaceReclaim;

    private ConcurrentHashMap< String, String > chunksSaved;
    private ConcurrentHashMap< String, String[] > initiatorVerifier;

    private String mcAddress;
    private int mcPort;
    private static Thread mcChannel;

    private String mdbAddress;
    private int mdbPort;
    private static Thread mdbChannel;

    private String mdrAddress;
    private int mdrPort;

    public static void main(String[] args) throws IOException, InterruptedException {

        peer = new Peer();

        if(!peer.validArgs(args))
            return;

        peer.initVars(args);
        peer.openChannels();

        peer.startPeer();
    }

    private boolean validArgs(String[] args){
        if (args.length != 7) {
            System.out.println("Usage: javan server.Peer  <ServerID> <MC address> <MC port> <MDB address> <MDB port> <MDR address> <MDR port>");
            return false;
        }

        return true;
    }

    private void initVars(String[] args){
        serverID = Integer.parseInt(args[0]);

        mcAddress = args[1];
        mcPort = Integer.parseInt(args[2]);

        mdbAddress = args[3];
        mdbPort =Integer.parseInt(args[4]);

        mdrAddress = args[5];
        mdrPort = Integer.parseInt(args[6]);
    }

    private void openChannels() throws IOException, InterruptedException {
        try {
            mcChannel = new Thread(new Mc(this.mcAddress, this.mcPort, this.peer));
            mdbChannel = new Thread(new Mdb(this.mcAddress, this.mcPort, this.peer));

            mcChannel.start();
            mdbChannel.start();
        }
        catch (IOException error) {
            System.out.println("Couldn't create the channels!");
        }
    }

    private void startPeer() throws IOException, InterruptedException {
        String read = new String();
        while (read != "end") {
            read = this.peer.receiveProtocol();
            this.peer.startProtocol(read);
        }
    }

    private String receiveProtocol() {
        return null;
    }

    private void startProtocol(String read) throws IOException, InterruptedException {
        String[] control = read.split(" ");
        if (control[0] == "Backup") {
            try {
                backup = new Thread(new BackupProtocol(control, this.peer));
                backup.start();
            }
            catch (IOException error) {
                System.out.println("Couldn't create the backupProtocol!");
            }
        } else if (control[0] == "Delete") {
            try {
                backup = new Thread(new DeleteProtocol(control, this.peer));
                backup.start();
            }
            catch (IOException error) {
                System.out.println("Couldn't create the backupProtocol!");
            }
        } else if (control[0] == "Restore") {
            try {
                backup = new Thread(new RestoreProtocol(control, this.peer));
                backup.start();
            }
            catch (IOException error) {
                System.out.println("Couldn't create the backupProtocol!");
            }
        } else if (control[0] == "SpaceReclaim") {
            try {
                backup = new Thread(new SReclaimProtocol(control, this.peer));
                backup.start();
            }
            catch (IOException error) {
                System.out.println("Couldn't create the backupProtocol!");
            }
        }
    }
}
