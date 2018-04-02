import java.io.File;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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


    //Knuth-Morris-Pratt Pattern Matching Algorithm
    public static int separation(byte[] data, byte[] pattern) {
        int[] failure = computeFailure(pattern);

        int j = 0;
        for (int i = 0; i < data.length; i++) {
            while (j > 0 && pattern[j] != data[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == data[i]) {
                j++;
            }
            if (j == pattern.length) {
                return i - pattern.length + 1;
            }
        }

        return -1;
    }

    private static int[] computeFailure(byte[] pattern) {
        int[] failure = new int[pattern.length];
        int j = 0;
        for (int i = 1; i < pattern.length; i++) {
            while (j>0 && pattern[j] != pattern[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == pattern[i]) {
                j++;
            }
            failure[i] = j;
        }
        return failure;
    }
}