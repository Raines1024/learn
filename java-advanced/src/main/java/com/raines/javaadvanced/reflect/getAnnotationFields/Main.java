package com.raines.javaadvanced.reflect.getAnnotationFields;

import com.raines.javaadvanced.reflect.getAnnotationFields.vo.DemoParam;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class Main {


    public static void main(String[] args) throws Exception {
        // 获取特定包下所有的类(包括接口和类)

    }

    /**
     * 输出所有使用了特定注解的类的注解值
     * @param clsList
     */
    public static void validAnnotationMethod(List<Class<?>> clsList){
        if (clsList != null && clsList.size() > 0) {
            for (Class<?> cls : clsList) {
                //获取类中的所有的方法
                Method[] methods = cls.getDeclaredMethods();
                if (methods.length > 0) {
                    for (Method method : methods) {
                        PeopleAnnotion peopleAnnotion = method.getAnnotation(PeopleAnnotion.class);
                        if (peopleAnnotion != null) {
                            //可以做权限验证
                            System.out.println(peopleAnnotion.say());
                        }
                    }
                }
            }
        }
    }


    /**
     * 输出所有使用了特定注解的字段
     * @param clsList
     */
    public static void validAnnotationFields(List<Class<?>> clsList){
        if (clsList != null && clsList.size() > 0) {
            for (Class<?> cls : clsList) {
                //获取类中的所有的字段
                Field[] fields = cls.getDeclaredFields();
                for (Field f : fields) {
                    // 判断字段注解是否存在
                    boolean annotationPresent2 = f.isAnnotationPresent(Fields.class);
                    if (annotationPresent2) {
                        Fields annotation = f.getAnnotation(Fields.class);
                        //字段名
                        System.out.println(f.getName());
                        //注解值
                        System.out.println(annotation.value());
                    }
                }
            }
        }
    }


}
