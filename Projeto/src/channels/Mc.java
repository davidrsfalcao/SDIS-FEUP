package channels;

import server.Peer;

import java.io.IOException;
import java.net.DatagramPacket;

public class Mc extends Channel implements Runnable {

  public Mc(String mcAddress, int mcPort, Peer peer) throws IOException, InterruptedException {
    super(mcAddress, mcPort, peer);
  }

  public void run() {
    System.out.println("Listening the MC channel...");

    try {
      super.getSocket().joinGroup(super.getAddress());

      while(true) {

      }
    }
    catch (IOException error) {
      error.printStackTrace();
    }

    //super.getSocket().leaveGroup(super.getAddress());
  }

  public byte[] receiveMessage() throws IOException {
    byte[] buf = new byte[256];
    DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
    this.socket.receive(msgPacket);

    //String msg = new String(buf, 0, buf.length);
    //System.out.println("Socket 1 received msg: " + msg);
    //i++;

    return buf;
  }

  public void sendMessage() throws IOException {
    String msg = "Sent message no 1";

    DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, this.address, this.multicastPort);
    this.socket.send(msgPacket);
  }
}

