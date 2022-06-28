package com.raines.javaadvanced.strategydesign;

import java.util.*;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * 税策略工厂，根据税类型获取不一样的税策略来算税，同时提供税策略注册接口
 */
public class AutoRegisterTaxStrategyFactory {
    // 存储税策略
    static Map<TaxType, TaxStrategy> taxStrategyMap = new HashMap<>();

    static {
        // 注册税策略
        autoRegisterTaxStrategy();
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

    // 提供税注册策略接口，外部只须要调用此接口接口新增税策略，而无需修改策略工厂内部代码
    public static void registerTaxStrategy(TaxType taxType, TaxStrategy taxStrategy) {
        taxStrategyMap.put(taxType, taxStrategy);
    }

    // 自动注册税策略
    private static void autoRegisterTaxStrategy() {
        try {
            // 经过反射找到全部的税策略子类进行注册
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(TaxStrategy.class.getPackage().getName()))
                    .setScanners(new SubTypesScanner()));
            Set<Class<? extends TaxStrategy>> taxStrategyClassSet = reflections.getSubTypesOf(TaxStrategy.class);

            if (taxStrategyClassSet != null) {
                for (Class<?> clazz: taxStrategyClassSet) {
                    TaxStrategy taxStrategy = (TaxStrategy)clazz.newInstance();
                    // 调用税策略的自注册方法
                    taxStrategy.register();
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            // 自行定义异常处理
            e.printStackTrace();
        }
    }
}