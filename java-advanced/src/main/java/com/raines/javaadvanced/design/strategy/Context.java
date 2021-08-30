package com.raines.javaadvanced.design.strategy;

import com.raines.javaadvanced.design.strategy.service.Strategy;

/**
 * 随着策略对象改变而改变的 context 对象。策略对象改变 context 对象的执行算法。
 */
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2){
        return strategy.doOperation(num1, num2);
    }
}
