package com.raines.javaadvanced.reflect.getAnnotationFields;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.FIELD)
public @interface Fields {
    int sort() default 0 ;
    String value() ;
}
