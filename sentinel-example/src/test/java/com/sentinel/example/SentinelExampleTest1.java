package com.sentinel.example;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * com.boot.cloud.SentinelExampleTest1
 *
 * @author lipeng
 * @date 2019-08-12 19:23
 */
public class SentinelExampleTest1 {

    private static final String RESOURCE_NAME = "HelloWorld";

    /**
     * 抛异常的方式定义资源
     */
    @Test
    public void test1() {
        initFlowRules();

        while (true) {
            try (Entry entry = SphU.entry(RESOURCE_NAME)) {
                // 被保护的逻辑
                System.err.println("HelloWorld");
            } catch (BlockException e) {
                // 处理被流控的逻辑
                System.err.println("blocked!");
            }
        }
    }

    /**
     * 返回boolean值的方式定义资源
     */
    @Test
    public void test2() {
        initFlowRules();
        while (true) {
            if (SphO.entry(RESOURCE_NAME)) {
                try {
                    System.err.println("HelloWorld");
                } finally {
                    SphO.exit();
                }
            } else {
                // 处理被流控的逻辑
                System.err.println("blocked!");
            }
        }
    }

    /**
     * 初始化流控规则
     */
    private void initFlowRules() {
        List<FlowRule> ruleList = new ArrayList<>();
        FlowRule rule = new FlowRule();
        // 此处的资源名称一定要和entry方法里面的名称一致，否则不起效
        rule.setResource(RESOURCE_NAME);
        // 基于qps进行流控
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 每秒20个请求
        rule.setCount(20);
        ruleList.add(rule);
        FlowRuleManager.loadRules(ruleList);
    }
}
