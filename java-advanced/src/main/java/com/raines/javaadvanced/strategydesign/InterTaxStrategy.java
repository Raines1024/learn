package com.raines.javaadvanced.strategydesign;

import com.raines.javaadvanced.strategydesign.third.TaxTypeAnnotation;

/**
 * 价内税策略，负责计算价内税
 */
public class InterTaxStrategy implements TaxStrategy {
    @Override
    public double calc(long amount) {
        final double taxRate = 0.2;  // 获取税率
        return amount * taxRate;
    }

    @Override
    public void register() {
        // 本身注册到策略工厂中
        AutoRegisterTaxStrategyFactory.registerTaxStrategy(TaxType.INTER, this);
    }
}
