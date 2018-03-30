package server;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class Peer implements RMI  {

    public static int serverID;
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

    ///////////
    private static int serviceAccessPoint;

    private static int MAXIMUM_SPACE = -1;
    private static int USED_SPACE = 0;
    ////////////////

    public static void main(String[] args) throws IOException, InterruptedException {

        peer = new Peer();

        if(!peer.validArgs(args))
            return;

        peer.initVars(args);
        peer.openChannels();

        //peer.startPeer();
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
        /*
        try {
            mcChannel = new Thread(new Mc(this.mcAddress, this.mcPort, this.peer));
            mdbChannel = new Thread(new Mdb(this.mcAddress, this.mcPort, this.peer));

            mcChannel.start();
            mdbChannel.start();
        }
        catch (IOException error) {
            System.out.println("Couldn't create the channels!");
        }
        */
    }

    /*private void startPeer() throws IOException, InterruptedException {
        String read = new String();
        while (read != "end") {
            read = this.peer.receiveProtocol();
            this.peer.startProtocol(read);
        }
    }*/

    private String receiveProtocol() {
        return null;
    }

    /*private void startProtocol(String read) throws IOException, InterruptedException {
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
    }*/

    @Override
    public void backup(String version, String senderId, String path, int replicationDegree) {
        /*
        try {
            backup = new Thread(new BackupProtocol(version, senderId, path, replicationDegree, this.peer));
            backup.start();
        }
        catch (IOException error) {
            System.out.println("Couldn't create the backupProtocol!");
        }
        catch (InterruptedException error) {
            System.out.println("Interrupted!");
        }
        */
    }

    @Override
    public void restore(String version, String senderId, String path) {
/*        try {
            restore = new Thread(new RestoreProtocol(version, senderId, path, this.peer));
            restore.start();
        }
        catch (IOException error) {
            System.out.println("Couldn't create the backupProtocol!");
        }
        catch (InterruptedException error) {
            System.out.println("Interrupted!");
        }*/
    }

    @Override
    public void delete(String version, String senderId, String path) {
/*        try {
            delete = new Thread(new DeleteProtocol(version, senderId, path, this.peer));
            delete.start();
        }
        catch (IOException error) {
            System.out.println("Couldn't create the backupProtocol!");
        }
        catch (InterruptedException error) {
            System.out.println("Interrupted!");
        }*/
    }

    @Override
    public void reclaim(String version, String senderId, int space) {
        /*
        try {
            spaceReclaim = new Thread(new SReclaimProtocol(version, senderId, space, this.peer));
            spaceReclaim.start();
        }
        catch (IOException error) {
            System.out.println("Couldn't create the backupProtocol!");
        }
        catch (InterruptedException error) {
            System.out.println("Interrupted!");
        }
        */
    }

    @Override
    public String state() {
        return null;
    }
}
