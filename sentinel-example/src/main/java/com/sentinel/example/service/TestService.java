package com.sentinel.example.service;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.sentinel.example.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * com.sentinel.example.service.TestService
 *
 * @author lipeng
 * @date 2019-08-19 11:32
 */
@Service
public class TestService {

    /**
     * 只有限流、熔断、系统保护的时候才会执行blockHandler
     */
    @SentinelResource(value = "block", blockHandler = "blockHandlerFallback")
    public String blockHandler(String name) throws BlockException {
        initFlowRules();
        if (Objects.equals(name, "block")) {
            // 模拟block异常
            for (int i = 0; i < 100; i++) {
                try (Entry entry = SphU.entry("block")) {
                    System.out.println("hello world");
                } catch (BlockException e) {
                    throw e;
                }
            }
        } else {
            throw new RuntimeException("exception");
        }
        return "hello " + name;
    }

    /**
     * 针对blockHandler的降级方法，参数中必须含有BlockException类型异常
     * @param name
     * @param blockException
     * @return
     */
    public String blockHandlerFallback(String name, BlockException blockException) {
        System.err.println("exception message：" + blockException.getMessage());
        return "blockHandlerFallback method，hello " + name;
    }

    /**
     * 任何异常都可以触发fallback，没有指定class，fallback方法必须在当前类中
     */
    @SentinelResource(value = "hello", fallback = "helloFallback")
    public String hello(String name) {
        throw new RuntimeException("trigger helloFallback method，hello " + name);
    }

    /**
     * 任何异常都可以触发defaultFallback，没有指定class，defaultFallback方法必须在当前类中
     */
    @SentinelResource(value = "test", defaultFallback = "testDefaultFallback")
    public String test(String name) {
        throw new RuntimeException("trigger testDefaultFallback method，hello " + name);
    }

    /**
     * 任何异常都可以触发defaultFallback
     * 走指定类的指定异常方法
     */
    @SentinelResource(value = "hi", fallback = "handleException", fallbackClass = ExceptionUtils.class)
    public String hi(String name) {
        throw new RuntimeException("trigger ExceptionUtils#handleException method，hello " + name);
    }

    @SentinelResource(value = "ignore", fallback = "handleException",
            fallbackClass = ExceptionUtils.class, exceptionsToIgnore = {IllegalArgumentException.class})
    public String ignore(String name) {
        if (Objects.equals(name, "ignore")) {
            throw new IllegalArgumentException("param is illegal");
        }
        throw new RuntimeException("trigger ExceptionUtils#handleException，hello " + name);
    }

    /**
     * 降级方法签名要和原方法一致
     *
     * @param name 参数
     * @return 参数
     */
    public String helloFallback(String name) {
        return "helloFallback method，hello " + name;
    }

    /**
     * defaultFallback降级方法，方法参数为空或者只能有一个Throwable类型的参数
     * 因为defaultFallback作为默认的降级方法，许多方法或者服务都可以使用，所以不能有参数
     *
     * @return 参数
     */
    public String testDefaultFallback(Throwable throwable) {
        System.err.println("exception message：" + throwable.getMessage());
        return "testDefaultFallback，hello";
    }

    private void initFlowRules() {
        List<FlowRule> ruleList = new ArrayList<>();
        FlowRule rule = new FlowRule();
        // 此处的资源名称一定要和entry方法里面的名称一致，否则不起效
        rule.setResource("block");
        // 基于qps进行流控
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 每秒20个请求
        rule.setCount(5);
        ruleList.add(rule);
        FlowRuleManager.loadRules(ruleList);
    }
}
