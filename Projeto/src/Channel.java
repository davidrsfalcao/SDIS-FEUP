import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class Channel {
  Thread thread;
  public MulticastSocket socket;
  public static InetAddress address;
	public static int multicastPort;

  public Channel(String address, int port)  throws IOException, InterruptedException  {
    //multicastAddressStr = args[0];
    this.multicastPort = port;
    MulticastSocket multicastSocket = new MulticastSocket(this.multicastPort);
		multicastSocket.setTimeToLive(1);
		InetAddress multicastAddress = InetAddress.getByName(address);
		//multicastSocket.joinGroup(multicastAddress);
  }

}
