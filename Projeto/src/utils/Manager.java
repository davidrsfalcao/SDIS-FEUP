package utils;
import server.Peer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Manager {

    private static String directory = "chunks/";

    public Manager() {
        super();
    }

    public static void saveChunk(String chunkNo, String fileId, byte[] chunk) {
        File file = new File(directory,fileId + "_" + chunkNo);
        file.getParentFile().mkdirs();
        try {
            FileOutputStream outFile = new FileOutputStream(file);
            outFile.write(chunk);
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int deleteChunk(String chunkNo, String fileId) {
        int size = (int) new File(directory + fileId + "_" + chunkNo).length();
        Path path = Paths.get(directory + fileId + "_" + chunkNo);
        try {
            Files.delete(path);
            return size;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void deleteFile(String fileId, Peer peer) throws IOException {
        if (!peer.chunksSaved.containsKey(fileId)) {
            return;
        }

        String[] chunks = peer.chunksSaved.get(fileId);

        for(int i=0; i< chunks.length; i++){
            deleteChunk(chunks[i],fileId);
        }


        peer.chunksSaved.remove(fileId);
    }

    public static byte[] getChunk(int chunkNo, String fileId) {

        Path path = Paths.get(directory + fileId + "_" + chunkNo);

        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean fileExists(String path) {
        File file = new File(path);

        return file.exists() && file.isFile();
    }

    public static final void deleteFile(String path) {
        File file = new File(path);

        file.delete();
    }


}