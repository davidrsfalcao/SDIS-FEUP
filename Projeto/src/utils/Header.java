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
        String final_string = "", tmp = "", SPACE = "  ";

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

    @Override
    public boolean equals(Object object){

        if(object == null)
            return false;

        if(getClass() != object.getClass())
            return false;

        Header header = (Header) object;

        /* Same Message Type */
        if(messageType.equals(null) && !header.getMessageType().equals(null))
            return false;

        if(!messageType.equals(header.getMessageType()))
            return false;

        /* Same SenderId */
        if(senderId.equals(null) && !header.getSenderId().equals(null))
            return false;

        if(!senderId.equals(header.getSenderId()))
            return false;

        /* Same FileId*/
        if(fileId.equals(null) && !header.getFileId().equals(null))
            return false;

        if(!fileId.equals(header.getFileId()))
            return false;

        /* Same ChunkNo */
        if(chunkNo.equals(null) && !header.getChunkNo().equals(null))
            return false;

        if(!chunkNo.equals(header.getChunkNo()))
            return false;

        return true;
    }

    @Override
    public int hashCode(){

        //TODO: verificar

        final int prime = 31;
        int result = 1;
        result = prime * result + ((chunkNo == null) ? 0 : chunkNo.hashCode());
        result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
        result = prime * result + ((senderId == null) ? 0 : senderId.hashCode());
        result = prime * result + ((messageType == null) ? 0 : messageType.hashCode());
        return result;
    }
}
