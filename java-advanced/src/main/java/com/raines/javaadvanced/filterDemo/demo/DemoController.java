package com.raines.javaadvanced.filterDemo.demo;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.raines.javaadvanced.aopDemo.annotation.CacheRefresh;
import com.raines.javaadvanced.expectionprocess.LogicException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {


    @CacheRefresh
    @GetMapping("/create")
    public boolean create() {
        return true;
    }

    private static void doSomething(){
        try(Entry entry = SphU.entry("doSomething")){
            //业务逻辑处理
            System.out.println("hello world"+System.currentTimeMillis());
        }catch (BlockException ex){
            //处理被流控的逻辑
            ex.printStackTrace();
        }
    }

    /**
     * 针对该保护的资源定义限流规则
     * 针对doSomething方法，每秒最多允许通过20个请求，也就是QPS为20
     */
    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        //设置需要保护的资源，必须和SphU.entry中使用的名称保持一致
        rule.setResource("doSomething");
        //限流阈值类型，QPS模式（1）或并发线程数模式（0）
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //限流阈值
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 限流测试
     * @param args
     */
    public static void main(String[] args) {
        initFlowRules();
        while (true){
            doSomething();
        }
    }

    /**
     * 熔断
     */
    private static void initDegradeRule(){
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource("KEY");
        degradeRule.setCount(10);
        //熔断策略，默认是秒级RT
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        //熔断降级的时间，单位是s。也就是触发熔断降级之后多长时间内自动熔断
        degradeRule.setTimeWindow(10);
        //触发的异常熔断最小请求数
        degradeRule.setMinRequestAmount(5);
        rules.add(degradeRule);
    }


    @GetMapping("/login")
    public boolean login() {
        throw new LogicException("error!!!");
    }


}
