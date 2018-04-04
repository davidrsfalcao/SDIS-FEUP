import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.IOException;

interface RMI extends Remote {
    public void backup(String version, String senderId, String path, int replicationDegree) throws RemoteException, IOException, InterruptedException ;
    public void restore(String version, String senderId, String path) throws RemoteException, IOException, InterruptedException ;
    public void delete(String version, int senderId, String path) throws RemoteException, IOException, InterruptedException ;
    public void reclaim(String version, String senderId, int space) throws RemoteException, IOException, InterruptedException ;
    public String state() throws RemoteException, IOException, InterruptedException ;
}