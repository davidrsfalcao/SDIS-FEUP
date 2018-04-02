package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.IOException;

public interface RMI extends Remote{
    public void backup(String version, int senderId, String path, int replicationDegree) throws RemoteException, IOException, InterruptedException ;

    public void restore(String version, int senderId, String path) throws RemoteException, IOException, InterruptedException ;

    public void delete(String version, int senderId, String path) throws RemoteException, IOException, InterruptedException ;

    public void reclaim(String version, int senderId, int space) throws RemoteException, IOException, InterruptedException ;

    public String state() throws RemoteException, IOException, InterruptedException ;
}