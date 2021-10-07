package com.raines.comm;

import java.util.function.Consumer;

public class Test {

    public static void main(String[] args) {
        Test t = new Test();
        t.sd();
    }
    
    public void sd(){
        demo(this::test);
    }

    private String test(String str) {
        System.out.println(str+"==");
        return str+"==";
    }


    public void demo(Consumer<String> consumer){
        consumer.accept("dsf");
    }
    
}
