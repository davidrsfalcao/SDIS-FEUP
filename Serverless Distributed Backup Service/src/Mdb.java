import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Random;
import java.util.concurrent.*;


public class Mdb extends Channel implements Runnable {
  String mcAdress;
  int mcPort;
  public MulticastSocket socketMc;
  public static InetAddress addressMc;

  public int sizeMsg;

  public Mdb(String mdbAddress, int mdbPort, Peer peer) throws IOException, InterruptedException {
		super(mdbAddress, mdbPort, peer);
	}

  public void run() {
    System.out.println("Listening the Mdb channel...");

    try {
      super.getSocket().joinGroup(super.getAddress());

      while (true) {
        System.out.println("MdbChannel Running...");
        byte[] chunk = receiveMessage();
        Random r = new Random();
        int time = r.nextInt(400);

        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
        scheduledPool.schedule(new Store(chunk, this.sizeMsg, this), time, TimeUnit.MILLISECONDS);
      }
    }
    catch (IOException error) {
      System.err.println("ERROR - "+ error.getMessage());
      error.printStackTrace();
    }
    //super.getSocket().leaveGroup(super.getAddress());
  }

  public byte[] receiveMessage() throws IOException {
    System.out.println("Receiving Mdb message...");
    byte[] buf = new byte[65000];
    DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
    this.socket.receive(msgPacket);
    this.sizeMsg = msgPacket.getLength();
    System.out.println("Message received in Mdb!");

    return buf;
  }

  public int divideChunk(byte[] chunk) {
    String crlf = Constants.CRLF + Constants.CRLF;
    return Utils.separation(chunk, crlf.getBytes());
  }

  public void storeChunk(String header, byte[] bodyTemp, int sizeTemp) {

    sizeTemp -= header.length();
    byte[] body = new byte[sizeTemp];
    System.arraycopy(bodyTemp, 0, body, 0, sizeTemp);
    String[] splittedHeader = header.split(" ");
    System.out.println("Message Type - " + splittedHeader[0]);
    if ( splittedHeader[2].equals( Integer.toString(peer.getServerID()) ) ) {
      System.out.println("Not storing (same Peer) !");
      return;
    }

    if ( this.getPeer().getChunksSaved().containsKey(splittedHeader[3]) ) {
      String[] chunkNumbersTemp = this.getPeer().getChunksSaved().get(splittedHeader[3]);
      boolean repeated = false;
      for (String chunks : chunkNumbersTemp) {
        if (chunks.equals(splittedHeader[4])) {
          repeated = true;
          return;
        }
      }

      if (!repeated) {
        String[] chunkNumbers = new String[chunkNumbersTemp.length + 1];
        for (int i = 0; i < (chunkNumbersTemp.length); i++) {
          chunkNumbers[i] = chunkNumbersTemp[i];
        }

        chunkNumbers[chunkNumbersTemp.length] = splittedHeader[4];
        this.getPeer().getChunksSaved().remove(splittedHeader[3]);
        this.getPeer().getChunksSaved().put(splittedHeader[3], chunkNumbers);
        System.out.println("Chunks - " + chunkNumbers.length);
      }
    }
    else {
        String[] chunkNumbers = new String[1];
        chunkNumbers[0] = splittedHeader[4];
        this.getPeer().getChunksSaved().put(splittedHeader[3], chunkNumbers);
    }

    Manager.saveChunk(splittedHeader[4] , splittedHeader[3], body);
    sendStored(splittedHeader);
  }

  private void sendStored(String[] splittedHeader) {
    byte[] stored = createConfirmation(splittedHeader);
    try {
      System.out.println("Sent Stored!");

      DatagramPacket msgPacket = new DatagramPacket(stored, stored.length, this.peer.getInetAddMc(), this.peer.getMcPort() );
      this.peer.getMdbSocket().send(msgPacket);

      System.out.println("Stored send!");
    }
    catch (IOException error) {
      error.printStackTrace();
    }
  }

  private byte[] createConfirmation(String[] splittedHeader) {
    Header tempHeader = new Header(Constants.STORED, "1.0", splittedHeader[2], splittedHeader[3], splittedHeader[4], splittedHeader[5]);
    String headerTemp = tempHeader.toStringStored();

    System.out.println(headerTemp);

    byte[] stored = headerTemp.getBytes();

    return stored;
  }
}

class Store implements Runnable {

  private byte[] chunk;
  private int sizeC;
  private Mdb mdb;

  public Store (byte[] chunk, int sizeC, Mdb inst) {
    this.chunk = chunk;
    this.sizeC = sizeC;
    this.mdb = inst;
  }

  public void run()
  {
    System.out.println("Store Thread started...");
    int half = mdb.divideChunk(chunk);
    System.out.println("Body starts here - " + half);
    String header = new String(chunk, 0, half);
    byte[] body = new byte[Constants.MAXCHUNKSIZE];
    System.arraycopy(chunk, half, body, 0,  Constants.MAXCHUNKSIZE);
    mdb.storeChunk(header, chunk, sizeC);
  }
};