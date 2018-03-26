package utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Manager {

    private static String directory = "chunks/";

    public Manager() {
        super();
    }

    public static void saveChunk(String chunkNo, String fileId, byte[] chunk) {
        File file = new File(directory,fileId + chunkNo);
        file.getParentFile().mkdirs();
        try {
            FileOutputStream outFile = new FileOutputStream(file);
            outFile.write(chunk);
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int deleteChunk(int chunkNo, String fileId) {
        int size = (int) new File(directory + fileId + chunkNo).length();
        Path path = Paths.get(directory + fileId + chunkNo);
        try {
            Files.delete(path);
            return size;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static byte[] getChunk(int chunkNo, String fileId) {

        Path path = Paths.get(directory + fileId + chunkNo);

        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
