package channels;

import server.Peer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class Channel {
  Peer peer;
  public MulticastSocket socket;
  public static InetAddress address;
  public static int multicastPort;

  public Channel(String address, int port, Peer peer)  throws IOException, InterruptedException  {
    this.peer = peer;
    this.multicastPort = port;
    this.socket = new MulticastSocket(this.multicastPort);
    this.socket.setTimeToLive(1);
    this.address = InetAddress.getByName(address);
  }

  public MulticastSocket getSocket() {
      return socket;
  }

  public static InetAddress getAddress() {
      return address;
  }
}
