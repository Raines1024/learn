package com.raines.springcloudsentinelsample.conf;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 手动配置流控规则
 */
public class FlowRuleInitFunc implements InitFunc {

    @Override
    public void init() throws Exception {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        //设置需要保护的资源，必须和SphU.entry中使用的名称保持一致
        rule.setResource("hello");
        //限流阈值类型，QPS模式（1）或并发线程数模式（0）
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //限流阈值
        rule.setCount(1);
        rule.setLimitApp("default");
        rules.add(rule);

        rule = new FlowRule();
        //设置需要保护的资源，必须和SphU.entry中使用的名称保持一致
        rule.setResource("dash");
        //限流阈值类型，QPS模式（1）或并发线程数模式（0）
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //限流阈值
        rule.setCount(1);
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
