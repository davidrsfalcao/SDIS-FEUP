package L01;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.net.DatagramPacket;
import java.io.IOException;

public class Server {
	private static int port;
	private static HashMap<String, String> plates;

	public static void main(String[] args) throws IOException {
		System.out.print("Entrei!\nl");
		System.out.print(args[0]);

		port = Integer.parseInt(args[0]);
		DatagramSocket socket = new DatagramSocket(port);

		byte[] buf = new byte[256];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);

		byte[] nbuf = packet.getData();
		String dString = new String(nbuf);
		String[] arrayString = dString.split(":");
		if (arrayString[0].compareTo("register") == 0 ) {
			if (plates.containsKey(arrayString[1]))
			plates.put(arrayString[1], arrayString[2]);
		} else if (arrayString[0].compareTo("lookup") == 0) {
			if (plates.containsKey(arrayString[1])) {
				arrayString[2] = plates.get(arrayString[1]);
				//response = arrayString[2];
				System.out.print("Done!\nl");
			}
		}

		System.out.print(plates);

		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		packet = new DatagramPacket(buf, buf.length, address, port);
		socket.send(packet);
	}
}
