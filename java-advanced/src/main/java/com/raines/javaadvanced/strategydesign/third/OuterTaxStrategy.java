package com.raines.javaadvanced.strategydesign.third;


import com.raines.javaadvanced.strategydesign.TaxType;

/**
 * 税策略去掉了注册方法，添加TaxTypeAnnotation注解来识别是哪一种税类型。
 * 价外税策略，负责计算价外税
 */
@TaxTypeAnnotation(taxType = TaxType.OUTER)
public class OuterTaxStrategy implements TaxStrategy {
    @Override
    public double calc(long amount) {
        final double taxRate = 0.2;  // 获取税率
        return amount / (1 + taxRate) * taxRate;
    }

}