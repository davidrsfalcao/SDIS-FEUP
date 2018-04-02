package utils;

import static utils.Constants.CRLF;

public class Header {
    private String messageType;
    private String version;
    private String senderId;
    private String fileId;
    private String chunkNo;
    private String replicationDeg;

    public Header(String messageType, String version, String senderId, String fileId, String chunkNo, String replicationDeg) {
        this.messageType = messageType;
        this.version = version;
        this.senderId = senderId;
        this.fileId = fileId;
        this.chunkNo = chunkNo;
        this.replicationDeg = replicationDeg;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getVersion() {
        return version;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getFileId() {
        return fileId;
    }

    public String getChunkNo() {
        return chunkNo;
    }

    public String getReplicationDeg() {
        return replicationDeg;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }


    public void setChunkNo(String chunkNo) {
        this.chunkNo = chunkNo;
    }

    @Override
    public String toString(){
        String final_string = "", tmp = "", SPACE = " ";

        /* <MessageType> <Version> <SenderId> <FileId> <ChunkNo> <ReplicationDeg> <CRLF> */

        tmp = this.getMessageType();
        if(tmp != null)
            final_string += tmp + SPACE;

        tmp = this.getVersion();
        if(tmp != null)
            final_string += tmp + SPACE;

        tmp = this.getSenderId();
        if(tmp != null)
            final_string += tmp + SPACE;

        tmp = this.getFileId();
        if(tmp != null)
            final_string += tmp + SPACE;

        tmp = this.getChunkNo();
        if(tmp != null)
            final_string += tmp + SPACE;

        tmp = this.getReplicationDeg();
        if(tmp != null)
            final_string += tmp + SPACE;


        final_string += CRLF + CRLF;

        return final_string;
    }


}