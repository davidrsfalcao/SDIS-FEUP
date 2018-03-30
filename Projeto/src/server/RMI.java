package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI extends Remote {

    void backup(String version, String senderId, String path, int replicationDegree) throws RemoteException;

    void restore(String version, String senderId, String path) throws RemoteException;

    void delete(String version, String senderId, String path) throws RemoteException;

    void reclaim(String version, String senderId, int space) throws RemoteException;

    String state() throws RemoteException;

}