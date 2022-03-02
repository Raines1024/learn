//package com;
//
//
//import jdk.internal.ref.Cleaner;
//import sun.nio.ch.DirectBuffer;
//
//import java.io.*;
//import java.nio.MappedByteBuffer;
//import java.nio.channels.FileChannel;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//public class Mp {
//
//    public static String path = "/opt/deploy/oss-file/uavfarm/BBC3A78747E14D0481E721257D5E342A/a.zip";
//
//    private static void createPath(String path) throws IOException {
//        List<File> toCreates = new ArrayList();
//
//        for(File tf = new File(path); tf != null && !tf.exists(); tf = tf.getParentFile()) {
//            toCreates.add(0, tf);
//        }
//
//        Iterator var3 = toCreates.iterator();
//
//        while(var3.hasNext()) {
//            File f = (File)var3.next();
//            f.createNewFile();
//        }
//
//    }
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//        // 将文件从一个目录移动到另一个目录
//        String param = "cd / & ls";
//        Process process = Runtime.getRuntime().exec(param);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        String line ="";
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//        }
////        File newFile = new File("/Users/raines/Desktop/my2/2.html");
////        if(file.renameTo(newFile)){
////            System.out.println("文件移动成功");;
////        }else{
////            System.out.println("文件移动失败");
////        }
//
////        System.out.println("开始");
////        for(int i=0;i<20;i++){
////            demo(i);
////        }
////        System.out.println("结束");
//    }
//
//    private static synchronized void demo(int i) throws IOException, InterruptedException {
//
//            File file1 = new File(path);
//            RandomAccessFile randomAccessFile = new RandomAccessFile(file1, "rw");
//            int len = 5*1024*1024;
//            byte[] fileData=new byte[len];
//            MappedByteBuffer mmap = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, i*len, len);
//            System.out.println(mmap.isReadOnly());
//            System.out.println(mmap.position());
//            System.out.println(mmap.limit());
//            mmap.put(fileData);
//            unmap(mmap);
//            randomAccessFile.close();
//            Thread.sleep(5000);
//    }
//
//    // copy from  FileChannelImpl#unmap(私有方法)
//    private static synchronized void unmap(MappedByteBuffer bb) {
//        Cleaner cl = ((DirectBuffer)bb).cleaner();
//        if (cl != null)
//            cl.clean();
//    }
//}
