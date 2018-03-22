

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Client {

	private static String multicastAddressStr;
	private static int multicastPort;

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println("Usage:");
			System.out
					.println("\tjava Client <multicastIP> <multicastPort> ");

			return;
		}

		multicastAddressStr = args[0];
		multicastPort = Integer.parseInt(args[1]);


		InetAddress group = InetAddress.getByName(multicastAddressStr);
		MulticastSocket multicastSocket = new MulticastSocket(multicastPort);
		multicastSocket.joinGroup(group);

		int i = 0;
		while (true) {
				 // Receive the information and print it.
				 byte[] buf = new byte[256];
				 DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
				 multicastSocket.receive(msgPacket);

				 String msg = new String(buf, 0, buf.length);
				 System.out.println("Socket 1 received msg: " + msg);
				 i++;

				 if(i > 45645){
					 break;
				 }
		}

		multicastSocket.leaveGroup(group);
		multicastSocket.close();
	}



}
