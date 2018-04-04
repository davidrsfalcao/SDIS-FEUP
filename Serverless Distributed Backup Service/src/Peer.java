import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Peer implements RMI  {

    public static int serverID;
    private static Peer peer;
    private static Thread backup;
    private static Thread delete;
    private static Thread restore;
    private static Thread spaceReclaim;

    private ConcurrentHashMap< String, String[] > chunksSaved = new ConcurrentHashMap<>(); //< FileId, ChunkNo[]>
    private ConcurrentHashMap< String, ConcurrentHashMap< Integer, String[] > > initiatorVerifier = new ConcurrentHashMap<>(); //< FileId, < ChunkNo, arrayServerIds> >

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

        try {
            RMI rmiObject = (RMI) UnicastRemoteObject.exportObject(peer, peer.serviceAccessPoint);
            Registry reg = LocateRegistry.getRegistry(1099);

            try {
                reg.rebind(Integer.toString(peer.serverID), rmiObject);
            }
            catch (RemoteException error) {
                reg = LocateRegistry.createRegistry(1099);
                reg.rebind(Integer.toString(peer.serverID), rmiObject); //Já nao falha
            }

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

        System.out.println(Integer.toString(peer.serverID));
        Thread[] threads = peer.openChannels();
        threads[0].join();
        threads[1].join();

    }

    private boolean validArgs(String[] args){
        if (args.length != 7) {
            System.out.println("Usage: java Peer  <ServerID> <MC address> <MC port> <MDB address> <MDB port> <MDR address> <MDR port>");
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

    private Thread[] openChannels() throws IOException, InterruptedException {
        try {
            mcChannel = new Thread(new Mc(this.mcAddress, this.mcPort, this.peer));
            mdbChannel = new Thread(new Mdb(this.mdbAddress, this.mdbPort, this.peer));

            mcChannel.start();
            mdbChannel.start();
            Thread[] threads = new Thread[2];
            threads[0] = mcChannel;
            threads[1] = mdbChannel;
            return threads;
        }
        catch (IOException error) {
            System.out.println("Couldn't create the channels!");
        }
        return null;
    }

    private String receiveProtocol() {
        return null;
    }

    @Override
    public void backup(String version, String senderId, String path, int replicationDegree) {
        System.out.println("Backup Protocol received");

            this.backup = new Thread(new BackupProtocol(version, senderId, "./files/test1Mb.db", replicationDegree, this.peer));
            this.backup.start();
    }

    @Override
    public void restore(String version, String senderId, String path) {
        try {
            restore = new Thread(new RestoreProtocol(version, senderId, path, this.peer));
            restore.start();
        }
        catch (IOException error) {
            System.out.println("");
        }
        catch (InterruptedException error) {
            System.out.println("Interrupted!");
        }
    }

    @Override
    public void delete(String version, String senderId, String path) {
        try {
            delete = new Thread(new DeleteProtocol(version, senderId, path, this.peer));
            delete.start();
        }
        catch (IOException error) {
            System.out.println("");
        }
        catch (InterruptedException error) {
            System.out.println("Interrupted!");
        }
    }

    @Override
    public void reclaim(String version, String senderId, int space) {
        try {
            spaceReclaim = new Thread(new SReclaimProtocol(version, senderId, space, this.peer));
            spaceReclaim.start();
        }
        catch (IOException error) {
            System.out.println("");
        }
        catch (InterruptedException error) {
            System.out.println("Interrupted!");
        }
    }

    @Override
    public String state() {
        return null;
    }


    public void manageHashMaps(String fileId) {
        if (!this.initiatorVerifier.containsKey(fileId)) {
            this.initiatorVerifier.put(fileId, new ConcurrentHashMap<>());
        }
    }


    ////Getters///
    public String getMdbAddress() {
        return mdbAddress;
    }

    public int getMdbPort() {
        return mdbPort;
    }

    public String getMcAddress() {
        return mcAddress;
    }

    public int getMcPort() {
        return mcPort;
    }

    public static int getServerID() {
        return serverID;
    }

    public ConcurrentHashMap<String, String[]> getChunksSaved() {
        return chunksSaved;
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<Integer, String[]>> getInitiatorVerifier() {
        return initiatorVerifier;
    }
}
