package utils;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.net.UnknownHostException;

public class Utils {

   /* public static String getFileId(File file){

        long lastModified = file.lastModified();

        String tmp = file.getName()+ Long.toString(lastModified);
        return DatatypeConverter.printHexBinary(sha256(tmp));
    }

    private static byte[] sha256(String file){

        MessageDigest message = null;

        try{
            message = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hash = new byte[0];

        try{
            hash = message.digest(file.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return hash;
    }*/

    public static String getIpAdress() throws UnknownHostException {
        InetAddress IP=InetAddress.getLocalHost();

        return IP.getHostAddress();
    }

}