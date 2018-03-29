import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

interface RMI extends Remote {
    public void backup(String version, String senderId, String path, int replicationDegree) throws RemoteException;
    public void restore(String version, String senderId, String path) throws RemoteException;
    public void delete(String version, String senderId, String path) throws RemoteException;
    public void reclaim(String version, String senderId, int space) throws RemoteException;
    public String state() throws RemoteException;
}