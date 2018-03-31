package utils;



import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.net.UnknownHostException;

public class Utils {

    public static String getFileId(File file){

        long lastModified = file.lastModified();

        String tmp = file.getName()+ Long.toString(lastModified);

        return sha256(tmp);
    }


    public static final String sha256(String str) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");

            byte[] hash = sha.digest(str.getBytes(StandardCharsets.UTF_8));

            StringBuffer hexStringBuffer = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);

                if (hex.length() == 1)
                    hexStringBuffer.append('0');

                hexStringBuffer.append(hex);
            }

            return hexStringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getIpAdress() throws UnknownHostException {
        InetAddress IP=InetAddress.getLocalHost();

        return IP.getHostAddress();
    }

}