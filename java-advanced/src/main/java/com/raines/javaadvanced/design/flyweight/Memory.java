package com.raines.javaadvanced.design.flyweight;

import lombok.Data;

@Data
public class Memory {

    //内存大小，单位为MB
    private int size;
    //内存是否在被使用
    private  boolean isused;
    //内存id
    private String id;

    public Memory(int size, boolean isused, String id) {
        this.size = size;
        this.isused = isused;
        this.id = id;
    }

}

