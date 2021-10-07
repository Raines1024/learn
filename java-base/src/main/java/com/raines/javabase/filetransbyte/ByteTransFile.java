package com.raines.javabase.filetransbyte;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * byte数组转换成文件
 */
public class ByteTransFile {
    private static final String UPLOAD_FOLDER = "/Users/raines/Desktop/my/demo/";

    public static void main(String[] args) {

        FileInputStream fileInputStream = null;

        try {

            File file = new File("abc.txt");
            byte[] bFile = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);

            //save bytes[] into a file
            writeBytesToFile(bFile, UPLOAD_FOLDER + "abc1.txt");
            writeBytesToFileClassic(bFile, UPLOAD_FOLDER + "abc2.txt");
            writeBytesToFileNio(bFile, UPLOAD_FOLDER + "abc3.txt");

            System.out.println("Done");

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
    }

    //Classic, < JDK7，传统方式
    private static void writeBytesToFileClassic(byte[] bFile, String fileDest) {
        FileOutputStream fileOuputStream = null;
        try {
            fileOuputStream = new FileOutputStream(fileDest);
            fileOuputStream.write(bFile);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOuputStream != null) {
                try {
                    fileOuputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //Since JDK 7 - try resources，传统方式
    private static void writeBytesToFile(byte[] bFile, String fileDest) {
        try (FileOutputStream fileOuputStream = new FileOutputStream(fileDest)) {
            fileOuputStream.write(bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Since JDK 7, NIO方式
    private static void writeBytesToFileNio(byte[] bFile, String fileDest) {
        try {
            Path path = Paths.get(fileDest);
            Files.write(path, bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
