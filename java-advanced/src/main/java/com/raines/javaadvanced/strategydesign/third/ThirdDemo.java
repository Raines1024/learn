package com.raines.javaadvanced.strategydesign.third;


import com.raines.javaadvanced.strategydesign.TaxType;

/**
 * 自注册策略模式
 * 基本思路是在税策略上使用注解说明是哪一种税类型，在税策略工厂中自动根据注解完成税策略注册，无需在每一个税策略中调用税策略工厂的注册接口。
 *
 * Map替代if实现策略选择，可提升扩展性，减小圈复杂度。
 * 自注册策略模式优雅的知足了开闭原则，对修改封闭，对扩展开放。
 * 越熟悉Java基础特性越能想到更好的方案，例如在文中使用的注解特性。因此平时应多学习JAVA基础特性，不要以为已经够用就不去了解新特性，新特性出来必定有它的优势，好比解决性能，优雅编码，解耦等等。
 */
public class ThirdDemo {
    public static void main(String[] args) throws Exception {
        TaxStrategy taxStrategy = AnnotationTaxStrategyFactory.getTaxStrategy(TaxType.INTER);
        System.out.println(taxStrategy.calc(100));
    }
}
