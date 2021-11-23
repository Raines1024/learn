package com.raines.javaadvanced.memory;

import sun.misc.Unsafe;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class Demo {

    public static void main(String[] args) throws InterruptedException{
        //分配128MB直接内存
        ByteBuffer bb = ByteBuffer.allocateDirect(1024*1024*128);
        TimeUnit.SECONDS.sleep(100);
        System.out.println("ok");
    }



}
