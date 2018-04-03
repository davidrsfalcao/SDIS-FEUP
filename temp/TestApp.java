import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TestApp{

    private static int peer_ap;
    private static String IP;
    private String sub_protocol;
    private String opnd_1;
    private int opnd_2;

    public static void main(String[] args){

        TestApp testApp  = new TestApp();

        if(!testApp .validArgs(args))
           return;

        System.out.println(args[0]);
        String[] ip_port = args[0].substring(2, args[0].lastIndexOf('/')).split(":");
        String ip = ip_port[0];
        int port = 1099;
        if (ip_port.length > 1) {
            port = Integer.parseInt(ip_port[1]);
        }
        String id = args[0].substring(args[0].lastIndexOf('/')+1);
        System.out.println("IP = " + ip + "\nPort = " + port + "\nID = " + id);

        try {
            IP = Utils.getIpAdress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        System.out.println(IP);
        RMI peer = null;

        try {
            Registry registry = LocateRegistry.getRegistry(ip, 1099);
            for (String name : registry.list()) {
                System.out.println("NAME - " + name);
            }
            peer = (RMI) registry.lookup(id);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("ERROR - "+ e.getMessage());
            e.printStackTrace();
            return;
        }

        testApp.init(peer);
    }


    private boolean validArgs(String[] args){
        if (args.length < 2) {
            System.out.println("Usage: java TestApp <peer_ap> <sub_protocol> <opnd_1> <opnd_2>\n");
            return false;
        }


        sub_protocol = args[1].toUpperCase();
    System.out.println("PICAS");
        switch(sub_protocol){
            case "BACKUP":
                if (args.length != 4){
                    System.out.println("Usage: java TestApp <peer_ap> BACKUP <filepath> <replication_degree>\n");
                    return false;
                }

                opnd_1 = args[2];

                try {
                    opnd_2 = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid replication_degree!\n");
                    System.out.println("Usage: java TestApp <peer_ap> BACKUP <filepath> <replication_degree>\n");
                    return false;
                }
                break;


            case "DELETE":
                if (args.length != 3){
                    System.out.println("Usage: java TestApp <peer_ap> DELETE <filepath>\n");
                    return false;
                }
                opnd_1 = args[2];
                break;

            case "RESTORE":
                if (args.length != 3){
                    System.out.println("Usage: java TestApp <peer_ap> RESTORE <filepath>\n");
                    return false;
                }
                opnd_1 = args[2];
                break;

            case "RECLAIM":
                if (args.length != 3)
                    System.out.println("Usage: java TestApp <peer_ap> RECLAIM <reclaim_space>\n");

                try {
                    opnd_2 = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid reclaim_space!\n");
                    System.out.println("Usage: java TestApp <peer_ap> RECLAIM <filepath> <replication_degree>\n");
                    return false;
                }
                break;

            case "STATE":
                break;

            default:
                System.out.println("Invalid sub_protocol!\n");
                System.out.println("Usage: java TestApp <peer_ap> <sub_protocol> <opnd_1> <opnd_2>\n");
                break;
        }

        return true;
    }

    private void init(RMI peer){

        switch(sub_protocol){
            case "BACKUP":
                try {
                    peer.backup("1.0", peer_ap+"", opnd_1, opnd_2);
                } catch (Exception e) {
                    System.err.println("Server exception: " + e.toString());
                    e.printStackTrace();
                }
                break;

            case "DELETE":
                try {
                    peer.delete("1.0", peer_ap, opnd_1);
                } catch (Exception e) {
                    System.err.println("Server exception: " + e.toString());
                    e.printStackTrace();
                }
                break;

            case "RESTORE":
                try {
                    peer.restore("1.0", peer_ap+"", opnd_1);
                } catch (Exception e) {
                    System.err.println("Server exception: " + e.toString());
                    e.printStackTrace();
                }
                break;

            case "RECLAIM":
                try {
                    peer.reclaim("1.0", peer_ap+"", opnd_2);
                } catch (Exception e) {
                    System.err.println("Server exception: " + e.toString());
                    e.printStackTrace();
                }
                break;

            case "STATE":
                try {
                    peer.state();
                } catch (Exception e) {
                    System.err.println("Server exception: " + e.toString());
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }
}