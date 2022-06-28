package com.raines.javaadvanced.strategydesign.second;

import com.raines.javaadvanced.strategydesign.AutoRegisterTaxStrategyFactory;
import com.raines.javaadvanced.strategydesign.TaxStrategy;
import com.raines.javaadvanced.strategydesign.TaxType;

/**
 * 二次优化 策略自动注册
 * 当添加新的税策略时，就彻底不须要修改已有的税策略工厂代码，基本完美作到开闭原则，惟一须要修改的是税类型定义。
 */
public class DecisionDemo {
    public static void main(String[] args) throws Exception {
        TaxStrategy taxStrategy = AutoRegisterTaxStrategyFactory.getTaxStrategy(TaxType.INTER);
        System.out.println(taxStrategy.calc(100));
    }
}