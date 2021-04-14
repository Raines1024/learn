package com.raines.javaadvanced.reflect.getAnnotationFields;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PeopleAnnotion {
    String say();
}
