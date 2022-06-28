package com.raines.javaadvanced.strategydesign;

/**
 * 税策略接口，提供算税接口，同时自注册到税策略工厂中
 *
 */
public interface TaxStrategy {
    double calc(long amount);
    // 新增自注册接口
    void register();
}
