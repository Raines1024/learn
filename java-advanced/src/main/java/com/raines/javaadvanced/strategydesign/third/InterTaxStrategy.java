package com.raines.javaadvanced.strategydesign.third;


import com.raines.javaadvanced.strategydesign.AutoRegisterTaxStrategyFactory;
import com.raines.javaadvanced.strategydesign.TaxType;

/**
 * 税策略去掉了注册方法，添加TaxTypeAnnotation注解来识别是哪一种税类型。
 * 价内税策略，负责计算价内税
 */
@TaxTypeAnnotation(taxType = TaxType.INTER)
public class InterTaxStrategy implements TaxStrategy {
    @Override
    public double calc(long amount) {
        final double taxRate = 0.2;  // 获取税率
        return amount * taxRate;
    }
}
