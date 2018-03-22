public class Chunk {
  private static final String put = "PUTCHANK";
  private static int senderId;
  private static int fileId;
  private static int chunkId;
  private static int replicationDeg;
  private static final String CRLF = "\r\n";
  private static Byte[] body;

  public Chunk(int senderId, int fileId, int chunkId, int replicationDeg, Byte[] body) {
    this.senderId = senderId;
    this.fileId = fileId;
    this.chunkId = chunkId;
    this.replicationDeg = replicationDeg;
    this.body = body;
  }

  public byte[] createChunk() {
    //juntar tudo num byte
  }
}
