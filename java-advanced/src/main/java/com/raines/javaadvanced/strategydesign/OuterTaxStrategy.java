package com.raines.javaadvanced.strategydesign;

import com.raines.javaadvanced.strategydesign.third.TaxTypeAnnotation;

/**
 * 价外税策略，负责计算价外税
 */
public class OuterTaxStrategy implements TaxStrategy {
    @Override
    public double calc(long amount) {
        final double taxRate = 0.2;  // 获取税率
        return amount / (1 + taxRate) * taxRate;
    }

    @Override
    public void register() {
        // 本身注册到策略工厂中
        AutoRegisterTaxStrategyFactory.registerTaxStrategy(TaxType.OUTER, this);
    }
}