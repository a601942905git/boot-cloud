package com.boot.cloud.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * com.boot.cloud.spring.RpcServiceRegistrar
 *
 * @author lipeng
 * @date 2020/4/4 10:21 AM
 */
public class RpcServiceRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableRpcService.class.getName()));
        RpcServiceClassPathScanner scanner = new RpcServiceClassPathScanner(registry);

        List<String> basePackages = new ArrayList<String>();
        for (String pkg : annotationAttributes.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }
}