package com.raines.javaadvanced.strategydesign.old;

import com.raines.javaadvanced.strategydesign.InterTaxStrategy;
import com.raines.javaadvanced.strategydesign.OuterTaxStrategy;
import com.raines.javaadvanced.strategydesign.TaxStrategy;
import com.raines.javaadvanced.strategydesign.TaxType;

/**
 * IF语句实现的税策略工厂
 * 若是经过if语句来获取不一样的税策略，当增长新的税策略时就不得不修改已有代码，当算税方法不少时，就不那么好看，同时也增长了圈复杂度。
 */
public class TaxStrategyFactory {
    public static TaxStrategy getTaxStrategy(TaxType taxType) throws Exception {
        // 当增长新的税类型时，须要修改代码，同时会增长圈复杂度
        if (taxType == TaxType.INTER) {
            return new InterTaxStrategy();
        } else if (taxType == TaxType.OUTER) {
            return new OuterTaxStrategy();
        } else {
            throw new Exception("The tax type is not supported.");
        }
    }
}