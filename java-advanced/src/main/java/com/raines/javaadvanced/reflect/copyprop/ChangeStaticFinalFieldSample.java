package com.raines.javaadvanced.reflect.copyprop;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 反射给final赋值（不建议使用）
 */
public class ChangeStaticFinalFieldSample {

    static void changeStaticFinal(Field field, Object newValue) throws Exception {
        field.setAccessible(true); // 如果field为private,则需要使用该方法使其可被访问

        Field modifersField = Field.class.getDeclaredField("modifiers");
        modifersField.setAccessible(true);
        // 把指定的field中的final修饰符去掉
        modifersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue); // 为指定field设置新值
    }

    public static void main(String[] args) throws Exception {
        Sample.print();

        Field canChangeField = Sample.class.getDeclaredField("CAN_CHANGE");
        Field cannotChangeField = Sample.class.getDeclaredField("CANNOT_CHANGE");
        Field strField = Sample.class.getDeclaredField("STR");
        changeStaticFinal(canChangeField, 2);
        changeStaticFinal(cannotChangeField, 3);
        changeStaticFinal(strField, "success");

        Sample.print();
    }
}

class Sample {
    private static final int CAN_CHANGE = new Integer(1); // 未内联优化
    private static final int CANNOT_CHANGE = 1; // 内联优化
    private static final String STR = new String("str");


    public static void print() {
        System.out.println("CAN_CHANGE = " + CAN_CHANGE);
        System.out.println("CANNOT_CHANGE = " + CANNOT_CHANGE);
        System.out.println("STR = " + STR);
        System.out.println("------------------------");
    }
}