package com.raines.javaadvanced.strategydesign.third;

import com.raines.javaadvanced.strategydesign.TaxType;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 此方式减小了税策略和税工厂的依赖，只须要关注本身的算法实现，解耦作得最好。
 */
public class AnnotationTaxStrategyFactory {
    // 存储税策略
    static Map<TaxType, TaxStrategy> taxStrategyMap = new HashMap<>();

    //初始化注册
    static {
        registerTaxStrategy();
    }

    // 经过map获取税策略，当增长新的税策略时无需修改代码，对修改封闭，对扩展开放，遵循开闭原则
    public static TaxStrategy getTaxStrategy(TaxType taxType) throws Exception {
        // 当增长新的税类型时，须要修改代码，同时增长圈复杂度
        if (taxStrategyMap.containsKey(taxType)) {
            return taxStrategyMap.get(taxType);
        } else {
            throw new Exception("The tax type is not supported.");
        }
    }

    // 自动注册税策略
    private static void registerTaxStrategy() {
        try {
            // 经过反射找到全部的税策略子类进行注册
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(TaxStrategy.class.getPackage().getName()))
                    .setScanners(new SubTypesScanner()));
            Set<Class<? extends TaxStrategy>> taxStrategyClassSet = reflections.getSubTypesOf(TaxStrategy.class);

            if (taxStrategyClassSet != null) {
                for (Class<?> clazz : taxStrategyClassSet) {
                    // 找到税类型注解，自动完成税策略注册
                    if (clazz.isAnnotationPresent(TaxTypeAnnotation.class)) {
                        TaxTypeAnnotation taxTypeAnnotation = clazz.getAnnotation(TaxTypeAnnotation.class);
                        TaxType taxType = taxTypeAnnotation.taxType();
                        taxStrategyMap.put(taxType, (TaxStrategy) clazz.newInstance());
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            // 自行定义异常处理
            e.printStackTrace();
        }
    }
}