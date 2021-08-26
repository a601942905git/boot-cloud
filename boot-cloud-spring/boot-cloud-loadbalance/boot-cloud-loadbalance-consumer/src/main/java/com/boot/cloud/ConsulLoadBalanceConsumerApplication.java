package com.boot.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * com.boot.cloud.LoadBalanceConsumerApplication
 *
 * @see org.springframework.web.client.RestTemplate 继承
 * @see org.springframework.http.client.support.InterceptingHttpAccessor InterceptingHttpAccessor持有拦截器集合
 *
 * @see org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration
 * LoadBalancerInterceptorConfig声明LoadBalancerInterceptor和RestTemplateCustomizer
 * 遍历RestTemplateCustomizer对RestTemplate进行个性化配置也就是设置拦截器为LoadBalancerInterceptor
 *
 * @see org.springframework.cloud.loadbalancer.config.BlockingLoadBalancerClientAutoConfiguration
 * @see org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration.BlockingSupportConfiguration
 *      @see org.springframework.cloud.loadbalancer.core.DiscoveryClientServiceInstanceListSupplier
 *      @see org.springframework.cloud.consul.discovery.ConsulDiscoveryClient
 *      @see com.ecwid.consul.v1.health.HealthConsulClient 调用接口http://localhost:8500/v1/health/service/service-name
 *
 *
 * @author lipeng
 * @date 2021/8/18 7:41 PM
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConsulLoadBalanceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsulLoadBalanceConsumerApplication.class, args);
    }
}
