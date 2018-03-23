import java.io.IOException;
import java.net.DatagramPacket;

public class Mdb extends Channel implements Runnable {
  public Mdb(String mdbAddress, int mdbPort) throws IOException, InterruptedException {
		super(mdbAddress, mdbPort);
		//this.thread = new McThread();
	}

  public void run() {
			System.out.println("Listening the Mdb channel...");

      //multicastSocket.joinGroup(group);
		/*	while(i<1000) {
        println("thread"+i);
      }*/
      System.out.println("thread 2");
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
