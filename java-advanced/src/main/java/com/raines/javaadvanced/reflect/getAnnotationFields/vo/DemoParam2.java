package com.raines.javaadvanced.reflect.getAnnotationFields.vo;

import com.raines.javaadvanced.reflect.getAnnotationFields.Fields;
import lombok.Data;

@Data
public class DemoParam2 {

    @Fields("d")
    private String c;

    private String d;

    public void say(){
        System.out.println("Demo2 say");
    }

}
