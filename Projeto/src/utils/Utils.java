package utils;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String getFileId(File file){

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
    }

}