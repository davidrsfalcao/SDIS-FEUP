package server;

import utils.RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Peer{

    private int serverID;
    private int serviceAccessPoint;

    private String mcAddress;
    private int mcPort;

    private String mdbAddress;
    private int mdbPort;

    private String mdrAddress;
    private int mdrPort;

    public void setMdbPort(int mdbPort) {
        this.mdbPort = mdbPort;
    }

    public static void main(String[] args){

        Peer peer = new Peer();

        if(!peer.validArgs(args))
            return;

        peer.initVars(args);

        ThreadPeer tread = new ThreadPeer(peer.serverID, peer.serviceAccessPoint, peer.mcAddress, peer.mcPort, peer.mdbAddress, peer.mdbPort, peer.mdrAddress, peer.mdrPort);
        tread.start();

        try {
            RMI rmiObject = (RMI) UnicastRemoteObject.exportObject(tread, peer.serviceAccessPoint);
            LocateRegistry.createRegistry(peer.serviceAccessPoint);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(Integer.toString(peer.serverID), rmiObject);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

    }


    private boolean validArgs(String[] args){
        if (args.length != 8) {
            System.out.println("Usage: java server.Peer <ServerID> <ServiceAccessPoint> <MC address> <MC port> <MDB address> <MDB port> <MDR address> <MDR port>");
            return false;
        }

        return true;
    }

    private void initVars(String[] args){
        serverID = Integer.parseInt(args[0]);

        mcAddress = args[1];
        mcPort = Integer.parseInt(args[2]);;

        mdbAddress = args[3];
        mdbPort =Integer.parseInt(args[4]);;

        mdrAddress = args[5];
        mdrPort = Integer.parseInt(args[6]);;
    }

}
