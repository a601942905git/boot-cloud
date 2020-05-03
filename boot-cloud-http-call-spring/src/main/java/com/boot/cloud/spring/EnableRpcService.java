package com.boot.cloud.spring;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * com.boot.cloud.spring.RpcServiceScan
 *
 * @author lipeng
 * @date 2020/4/4 10:20 AM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RpcServiceRegistrar.class)
public @interface EnableRpcService {

    /**
     * 扫描包路径
     * @return
     */
    String[] basePackages() default {};
}
