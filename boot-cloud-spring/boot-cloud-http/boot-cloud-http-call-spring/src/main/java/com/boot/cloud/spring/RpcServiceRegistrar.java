package com.boot.cloud.spring;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * com.boot.cloud.spring.RpcServiceRegistrar
 *
 * @author lipeng
 * @date 2020/4/4 10:21 AM
 */
public class RpcServiceRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, @NotNull BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableRpcService.class.getName()));
        if (Objects.isNull(annotationAttributes)) {
            throw new NullPointerException("please use @EnableRpcService annotation");
        }

        RpcServiceClassPathScanner scanner = new RpcServiceClassPathScanner(registry);

        List<String> basePackages = new ArrayList<>();
        for (String pkg : annotationAttributes.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }
}