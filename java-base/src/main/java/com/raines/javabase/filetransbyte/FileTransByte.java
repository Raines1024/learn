package com.raines.javabase.filetransbyte;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * 文件转化成byte数组
 */
public class FileTransByte {

    //nio方式
    private static byte[] nioStyle(String filePath) throws Exception {
        byte[] bFile = Files.readAllBytes(new File(filePath).toPath());
        //or this
//        byte[] bFile = Files.readAllBytes(Paths.get(filePath));
        return bFile;
    }

    //传统方式
    private static byte[] traditionalStyle() throws Exception {
        File file = new File("/Users/raines/Desktop/my/demo/abc.txt");
        //init array with file length
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();

        return bytesArray;
    }

    public static void main(String[] args) {

        try {

            // convert file to byte[]
            byte[] bFile = readBytesFromFile("/Users/raines/Desktop/my/demo/abc.txt");

            //java nio
            //byte[] bFile = Files.readAllBytes(new File("test.txt").toPath());
            //byte[] bFile = Files.readAllBytes(Paths.get("test.txt"));

            // save byte[] into a file
            Path path = Paths.get("/Users/raines/Desktop/my/demo/abc2.txt");
            Files.write(path, bFile);

            System.out.println("Done");

            //Print bytes[]
            for (int i = 0; i < bFile.length; i++) {
                System.out.print((char) bFile[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 传统方式
     * @param filePath
     * @return
     */
    private static byte[] readBytesFromFile(String filePath) {
        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;
        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;

    }

}
