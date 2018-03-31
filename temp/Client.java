import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client{

    private int serverID;
    private int serviceAccessPoint;

    private String mcAddress;
    private int mcPort;

    private String mdbAddress;
    private int mdbPort;

    private String mdrAddress;
    private int mdrPort;

    private static final String IPRMI =  "172.30.27.59";


    public void setMdbPort(int mdbPort) {
        this.mdbPort = mdbPort;
    }

    public static void main(String[] args){

        Client client = new Client();

        if(!client.validArgs(args))
            return;

        client.initVars(args);
        RMI peer = null;

        try {
            Registry registry = LocateRegistry.getRegistry(IPRMI, 1099);
            for (String name : registry.list()) {
                System.out.println("NAME - " + name);
            }
            peer = (RMI) registry.lookup("0");
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("ERROR - "+ e.getMessage());
            e.printStackTrace();
        }

        try {
            peer.backup("1.0", "1", "/path", 3);
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }


    private boolean validArgs(String[] args){
        if (args.length != 8) {
            System.out.println("Usage: java server.Client <ServerID> <ServiceAccessPoint> <MC address> <MC port> <MDB address> <MDB port> <MDR address> <MDR port>");
            return false;
        }

        return true;
    }

    private void initVars(String[] args){
        serverID = Integer.parseInt(args[0]);

        mcAddress = args[2];
        mcPort = Integer.parseInt(args[3]);;

        mdbAddress = args[4];
        mdbPort =Integer.parseInt(args[5]);;

        mdrAddress = args[6];
        mdrPort = Integer.parseInt(args[7]);;
    }

}