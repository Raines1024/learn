package com.raines.javabase;

import java.util.ArrayList;
import java.util.List;

public class Demo {

     {
        System.out.println("========");
    }

    {
        System.out.println("dsf");
    }



    public static void main(String[] args) {
        List<Demo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Demo demo = new Demo();
            list.add(demo);
        }
    }
}
