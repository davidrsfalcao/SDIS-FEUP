package channels;

import server.Peer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Mdb extends Channel implements Runnable {
  String mcAdress;
  int mcPort;
  public MulticastSocket socketMc;
  public static InetAddress addressMc;

  public Mdb(String mdbAddress, int mdbPort, Peer peer) throws IOException, InterruptedException {
    super(mdbAddress, mdbPort, peer);
  }

  public void run() {
    System.out.println("Listening the Mdb channel...");

    try {
      super.getSocket().joinGroup(super.getAddress());
      while (true) {
        mcResult(receiveMessage());
      }
    }
    catch (IOException error) {
      System.err.println("ERROR - "+ error.getMessage());
      error.printStackTrace();
    }
    //super.getSocket().leaveGroup(super.getAddress());
  }

  public byte[] receiveMessage() throws IOException {
    byte[] buf = new byte[65000];
    DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
    this.socket.receive(msgPacket);

    //String msg = new String(buf, 0, buf.length);
    System.out.println("Socket 1 received!");
    //i++;

    return buf;
  }

  public void sendMessage() throws IOException {
    String msg = "Sent message no 1";

    DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, this.address, this.multicastPort);
    this.socket.send(msgPacket);
  }

  public void mcResult(byte[] chunk) {

    try {
      mcAdress = peer.getMcAddress();
      mcPort = peer.getMcPort();
      socketMc = new MulticastSocket(mcPort);
      socketMc.setTimeToLive(1);
      addressMc = InetAddress.getByName(mcAdress);
    }
    catch (IOException error) {
      error.printStackTrace();
    }
  }
}
