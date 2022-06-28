package com.raines.javaadvanced.strategydesign.first;

import com.raines.javaadvanced.strategydesign.InterTaxStrategy;
import com.raines.javaadvanced.strategydesign.OuterTaxStrategy;
import com.raines.javaadvanced.strategydesign.TaxStrategy;
import com.raines.javaadvanced.strategydesign.TaxType;

import java.util.HashMap;
import java.util.Map;

/**
 * 首次优化 税策略工厂中使用Map替代if
 * 进化后IF语句没有了，减小了圈复杂度，增长新的策略后只需调用策略注册接口就好，不须要修改获取税策略的代码。
 * 要注册新的税策略，必须手动调用MapTaxStrategyFactory的注册接口，这样，每新增长一个税策略都须要修改已有代码，或者要找到一个合适的初始化调用点，去注册税策略，如何能完美的符合开闭原则，对修改关闭，对扩展开放呢？
 */
public class MapTaxStrategyFactory {
    // 存储税策略
    static Map<TaxType, TaxStrategy> taxStrategyMap = new HashMap<>();

    // 注册默认税策略
    static {
        registerTaxStrategy(TaxType.INTER, new InterTaxStrategy());
        registerTaxStrategy(TaxType.OUTER, new OuterTaxStrategy());
    }

    // 提供税注册策略接口，外部只须要调用此接口接口新增税策略，而无需修改策略工厂内部代码
    public static void registerTaxStrategy(TaxType taxType, TaxStrategy taxStrategy) {
        taxStrategyMap.put(taxType, taxStrategy);
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
}
