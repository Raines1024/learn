package com.raines.javaadvanced.reflect.getAnnotationFields.vo;

import com.raines.javaadvanced.reflect.getAnnotationFields.Fields;
import com.raines.javaadvanced.reflect.getAnnotationFields.PeopleAnnotion;
import lombok.Data;

@Data
public class DemoParam {

    @Fields("d")
    private String a;

    private String b;

    @PeopleAnnotion(say="Black")
    public void say(){
        System.out.println("Demo say");
    }

}
