package com.raines.javaadvanced.strategydesign.third;

import com.raines.javaadvanced.strategydesign.TaxType;

import java.lang.annotation.*;

/**
 * 税策略去掉了注册方法，添加TaxTypeAnnotation注解来识别是哪一种税类型
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TaxTypeAnnotation {
    TaxType taxType();
}