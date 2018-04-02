package channels;

import server.Peer;
import utils.Manager;
import utils.Utils;

import static utils.Constants.MAXCHUNKSIZE;
import static utils.Constants.CRLF;

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

  public Mdb(String mdbAddress, int mdbPort, Peer peer) throws IOException, InterruptedException {
    super(mdbAddress, mdbPort, peer);
    responceMc();
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

        Runnable store = new Runnable() {
          @Override
          public void run()
          {
            System.out.println("Store Thread started...");
            int half = divideChunk(chunk);
            System.out.println("Body starts here - " + half);
            String header = new String(chunk, 0, half);
            byte[] body = new byte[MAXCHUNKSIZE];
            System.arraycopy(chunk, half, body, 0, MAXCHUNKSIZE);
            storeChunk(header, body);
          }
        };

        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
        scheduledPool.schedule(store, time, TimeUnit.MILLISECONDS);
      }
    }
    catch (IOException error) {
      System.err.println("ERROR - "+ error.getMessage());
      error.printStackTrace();
    }
    //super.getSocket().leaveGroup(super.getAddress());
  }

  public byte[] receiveMessage() throws IOException {
    System.out.println("Receiving message...");
    byte[] buf = new byte[65000];
    DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
    this.socket.receive(msgPacket);

    System.out.println("Message received!");

    return buf;
  }

  public void sendMessage() throws IOException {
    String msg = "Sent message no 1";

    DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, this.address, this.multicastPort);
    this.socket.send(msgPacket);
  }

  public void responceMc() {

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

  public int divideChunk(byte[] chunk) {
    String crlf = CRLF + CRLF;
    return Utils.separation(chunk, crlf.getBytes());
  }

  public void storeChunk(String header, byte[] body) {
    String[] splittedHeader = header.split(" ");
    System.out.println("Message Type - " + splittedHeader[0]);
    /*if ( splittedHeader[2].equals( Integer.toString(peer.getServerID()) ) ) {
      System.out.println("Not storing (same Peer) !");
      return;
    }*/

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
    //Create stored
    byte[] stored = new byte[2];
    try {
      this.socket.joinGroup(this.addressMc);

      DatagramPacket msgPacket = new DatagramPacket(stored, stored.length, this.addressMc, this.mcPort);
      this.socket.send(msgPacket);

      this.socket.leaveGroup(this.addressMc);
    }
    catch (IOException error) {
      error.printStackTrace();
    }
  }
}