package L01;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

	private static String hostName;
	private static int port;
	private static String oper;
	private static String plate, owner;

	public static void main(String[] args) throws IOException {
		/* Verificar argumentos */
		if (!verificarArgs(args))
			return;

		// criar request
		String request = oper;

		switch (oper) {
		case "lookup":
			request += ":" + plate;
			break;

		case "register":
			request += ":" + plate + ":" + owner;
			break;
		}

		// abrir socket
		DatagramSocket socket = new DatagramSocket();

		byte[] buf = request.getBytes();
		InetAddress address = InetAddress.getByName(hostName);
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address,port);
		socket.send(packet);

		System.out.println("Request enviado: "+ request);



		// fechar socket
		socket.close();

	}

	private static boolean verificarArgs(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: java Client <hostname> <port> <oper> <opnd>*");

			return false;
		} else {
			hostName = args[0];
			System.out.println("\n\tHost name: " + hostName);
			port = Integer.parseInt(args[1]);
			System.out.println("\tPort: " + port);

			String operStr = args[2];
			if (operStr.compareTo("register") == 0) {
				if (args.length != 5) {
					System.out.println("Usage: java Client <hostname> <port> register <plate number> <owner name>");

					return false;
				}

				oper = "register";
				plate = args[3];
				owner = args[4];

				System.out.println("Register " + plate + " " + owner);

			} else if (operStr.compareTo("lookup") == 0) {
				if (args.length != 4) {
					System.out.println("Usage: java Client <hostname> <port> lookup <plate number>");

					return false;
				}

				oper = "lookup";
				plate = args[3];

				System.out.println("Look up: " + plate);
			} else {
				System.out.println("Usage: java Client <hostname> <port> <oper> <opnd>*");

				return false;
			}
		}

		return true;
	}

}
