package server;

import utils.Manager;
import utils.RMI;
import utils.Utils;


import java.io.File;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadPeer extends Thread implements RMI {

    public static int serverID;
    private static int serviceAccessPoint;

    private static int MAXIMUM_SPACE = -1;
    private static int USED_SPACE = 0;

    public static ConcurrentHashMap<String, String> fileIds;
    public static ConcurrentHashMap<String, Integer> requestsFileReplication;


    ThreadPeer(int serverID, int serviceAccessPoint, String mcAddress, int mcPort, String mdbAddress, int mdbPort, String mdrAddress, int mdrPort) {

        this.serverID = serverID;
        this.serviceAccessPoint = serviceAccessPoint;


        // TODO



    }

    private static void freeUpSpace() {
        if (MAXIMUM_SPACE >= USED_SPACE) return;

        System.out.println("Used space exceed the limit");

        // TODO
    }

    @Override
    public void backup(String version, String senderId, String path, int replicationDegree) throws RemoteException {

        String fileId = Utils.getFileId(new File(path));
        fileIds.put(path, fileId);
        requestsFileReplication.put(fileId, replicationDegree);

        // TODO : Manager.backupFile(path, replicationDegree);
    }

    @Override
    public void restore(String version, String senderId, String path) throws RemoteException {

    }

    @Override
    public void delete(String version, String senderId, String path) throws RemoteException {

    }

    @Override
    public void reclaim(String version, String senderId, int space) throws RemoteException {
        MAXIMUM_SPACE = space;
        freeUpSpace();
    }

    @Override
    public String state() throws RemoteException {
        return null;
    }
}
