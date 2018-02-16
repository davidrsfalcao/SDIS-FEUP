package L01;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.io.IOException;

public class Server {
	private static int port;
	public static void main(String[] args) throws IOException {
		port = Integer.parseInt(args[1]);
		DatagramSocket socket = new DatagramSocket(port);
		
		byte[] buf = new byte[256];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);
		
		byte[] nbuf = packet.getData();
		String dString = new String(nbuf);
		/*
		String dString = null;
		if (in == null)
		    dString = new Date().toString();
		else
		    dString = getNextQuote();
		buf = dString.getBytes();*/
		
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		packet = new DatagramPacket(buf, buf.length, address, port);
		socket.send(packet);
	}
}
