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
        byte[] msg = receiveMessage();

        String message = new String(msg);

        String[] message_split = message.split(" ");

        switch (message_split[0]){
          case "DELETE":
            int senderId = Integer.parseInt(message_split[2]);

            if(this.getPeer().getServerID() != senderId){
              String fileId = message_split[3];
              try {
                  System.out.println(fileId);
                  Manager.deleteFile(fileId,this.getPeer());
              } catch (IOException e) {
                e.printStackTrace();
              }

            }

            break;

          case "STORED":
            System.out.println(message);
            System.out.println(message_split[2]);
            System.out.println(this.getPeer().getServerID());
            if (message_split[2].equals(""+this.getPeer().getServerID())) {
              System.out.println("Sou eu que trato!");
              initVerifier(message_split[3], message_split[4]);
            }
            break;

        }
      }
    }
    catch (IOException error) {
      error.printStackTrace();
    }

    //super.getSocket().leaveGroup(super.getAddress());
  }

  public byte[] receiveMessage() throws IOException {
    System.out.println("Receiving Mc message...");
    byte[] buf = new byte[100];
    DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
    this.socket.receive(msgPacket);

    System.out.println("Message received in Mc!");

    return buf;
  }

  public void sendMessage() throws IOException {
    String msg = "Sent message no 1";

    DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, this.address, this.multicastPort);
    this.socket.send(msgPacket);
  }

  public void initVerifier(String id, String number) {
    if ( this.getPeer().initiatorVerifier.get(id).containsKey(number) ) {

    }

  }
}
