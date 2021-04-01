package com.raines.javaadvanced.reflect.copyprop;

import lombok.Data;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.*;

public class BeanUtil {

    public static void main(String[] args) {

        User1 u1 = new User1();
        u1.setUserId(1);
        u1.setUserName("raines");
        u1.setDate(new Date());
        List list = new ArrayList();
        list.add("oooooooo");
        u1.setList(list);

        long start, end;

        User2 u3 = new User2();
        start = System.currentTimeMillis();
        copyFiled(u1, u3);
        end = System.currentTimeMillis();
        System.out.println("用时" + (end - start) + "毫秒");

        User2 u4 = new User2();
        start = System.currentTimeMillis();
        copyFiled2(u1, u4);
        end = System.currentTimeMillis();
        System.out.println("用时" + (end - start) + "毫秒");


        User2 u5 = new User2();
        start = System.currentTimeMillis();
        copyFiled3(u1, u5);
        end = System.currentTimeMillis();
        System.out.println("用时" + (end - start) + "毫秒");


        User2 u2 = new User2();
        start = System.currentTimeMillis();
        org.springframework.beans.BeanUtils.copyProperties(u1, u2);
        end = System.currentTimeMillis();
        System.out.println("BeanUtils.copyProperties用时" + (end - start) + "毫秒");
    }

    /**
     * 利用两个数组迭代复制
     */
    public static void copyFiled(Object source, Object target) {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = target.getClass().getDeclaredFields();

        Object value;
        int count = 0;
        try {
            for (Field field : sourceFields) {
                for (Field field2: targetFields) {
                    count++;
                    if (field.getName().equals(field2.getName()) && field.getType().equals(field2.getType())) {

                        field.setAccessible(true);
                        value = field.get(source);
                        if (null == value || "" == value) {
                            continue;
                        }
                        field2.setAccessible(true);
                        field2.set(target, value);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("迭代次数: " + count);
    }

    /**
     * 利用一个数组和一个链表迭代复制
     *
     * @param source
     * @param target
     */
    public static void copyFiled2(Object source, Object target) {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = target.getClass().getDeclaredFields();

        List<Field> link = new LinkedList<>(Arrays.asList(targetFields));
        Object value;
        int count = 0;
        Field field2;
        int len;
        try {
            for (Field field : sourceFields) {
                len = link.size();
                for (int i = 0; i < len; i++) {
                    count++;
                    field2 = link.get(i);
                    if (field.getName().equals(field2.getName()) && field.getType().equals(field2.getType())) {

                        field.setAccessible(true);
                        value = field.get(source);
                        if (null == value || "" == value) {
                            continue;
                        }
                        field2.setAccessible(true);
                        field2.set(target, value);
                        link.remove(i);
                        break;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("迭代次数: " + count);
    }

    /**
     * 利用一个数组和一个HashMap迭代复制
     */
    public static void copyFiled3(Object source, Object target) {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = target.getClass().getDeclaredFields();

        Map<String, Field> fieldMap = new HashMap<>(targetFields.length);
        for (Field targetField : targetFields) {
            fieldMap.put(targetField.getName(), targetField);
        }
        Object value;
        int count = 0;
        Field field2;
        try {
            for (Field field : sourceFields) {
                count++;
                field2 = fieldMap.get(field.getName());
                if (field.getType().equals(field2.getType())) {

                    field.setAccessible(true);
                    value = field.get(source);
                    if (null == value || "" == value) {
                        continue;
                    }
                    field2.setAccessible(true);
                    field2.set(target, value);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("迭代次数: " + count);
    }
}

@Data
class User1 {

    private Date date;

    private Integer userId;

    private String userName;

    private List list;

}
@Data
class User2 {

    private Integer userId;

    private String userName;

    private Date date;

    private List list;

}