import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Server {

	private static String multicastAddressStr;
	private static int multicastPort;

	private static HashMap<String, String> plates;

	public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length != 2) {
			System.out.println("Usage:");
			System.out.println("\tjava Server <multicastIP> <multicastPort>");

			return;
		}

		multicastAddressStr = args[0];
		multicastPort = Integer.parseInt(args[1]);



		// open multicast socket
		MulticastSocket multicastSocket = new MulticastSocket(multicastPort);
		multicastSocket.setTimeToLive(1);

		InetAddress multicastAddress = InetAddress.getByName(multicastAddressStr);

		multicastSocket.joinGroup(multicastAddress);

		int i = 0;
		while(true){
			String msg = "Sent message no " + i;

			// Create a packet that will contain the data
			// (in the form of bytes) and send it.
			DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, multicastAddress, multicastPort);
			multicastSocket.send(msgPacket);

			System.out.println("Server sent packet with msg: " + msg);
			Thread.sleep(500);
			i++;

			if(i > 45645){
				break;
			}
		}


		// close multicast socket
		multicastSocket.close();
	}


}
