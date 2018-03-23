package server;

public class Peer {

    private int serverID;

    private String mcAddress;
    private int mcPort;

    private String mdbAddress;
    private int mdbPort;

    private String mdrAddress;
    private int mdrPort;


    public static void main(String[] args){

        Peer peer = new Peer();

        if(!peer.validArgs(args))
            return;

        peer.initVars(args);

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
        mcPort = Integer.parseInt(args[2]);;

        mdbAddress = args[3];
        mdbPort =Integer.parseInt(args[4]);;

        mdrAddress = args[5];
        mdrPort = Integer.parseInt(args[6]);;
    }


}
