package com.boot.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * com.boot.cloud.LoadBalanceApplication
 *
 * 服务注册流程
 * @see org.springframework.cloud.consul.serviceregistry.ConsulAutoServiceRegistrationListener 实现
 * @see org.springframework.context.event.SmartApplicationListener 接口，应用启动会调用onApplicationEvent()方法
 *
 * @see org.springframework.cloud.consul.serviceregistry.ConsulAutoServiceRegistration
 *      @see org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry 服务注册
 *          @see com.ecwid.consul.v1.agent.AgentConsulClient 调用/v1/agent/service/register接口注册服务
 *  @see org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration 注册表，含有要注册服务信息
 *
 * 服务反注册流程
 * @see org.springframework.cloud.consul.serviceregistry.ConsulAutoServiceRegistration 父类定义了销毁方法destroy()
 *      @see org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry 服务注册
 *      @see com.ecwid.consul.v1.agent.AgentConsulClient 调用/v1/agent/service/deregister/service-id接口注册服务
 * @author lipeng
 * @date 2021/8/18 7:35 PM
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConsulLoadBalanceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsulLoadBalanceProviderApplication.class, args);
    }
}
