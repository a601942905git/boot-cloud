package com.boot.cloud.openfeign.listener;

import com.ecwid.consul.v1.ConsistencyMode;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.HealthServicesRequest;
import com.ecwid.consul.v1.health.model.HealthService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.consul.discovery.ConsulServiceInstance;
import org.springframework.cloud.loadbalancer.cache.LoadBalancerCacheManager;
import org.springframework.cloud.loadbalancer.core.CachingServiceInstanceListSupplier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * com.boot.cloud.openfeign.listener.ConsulServiceWatch
 * consul健康服务列表监听
 * 1.轮询调用http://localhost:8500/v1/health/service/openfeign-provider-service?index=30&passing=1&stale=&wait=600000ms接口
 * 2.index：请求资源的唯一标识；passing：查询健康服务列表；stale：非leader节点也可以提供查询服务；
 *   wait：等待时间，客户端发起请求后，请求会被挂起，直到请求资源发生变更或者请求时间超过wait参数指定时间，请求才会被响应
 *
 *
 * @author lipeng
 * @date 2021/12/28 3:55 PM
 */
@Component
@Slf4j
public class ConsulHealthServiceWatch implements SmartLifecycle, ApplicationContextAware {

    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * 等待时间单位s
     * @see com.ecwid.consul.v1.QueryParams#toUrlParameters()
     */
    public static final long WAIT_TIME = 60L;

    private ApplicationContext applicationContext;

    public static final String CACHE_NAME = CachingServiceInstanceListSupplier.SERVICE_INSTANCE_CACHE_NAME;

    public static final String SERVICE_NAME = "openfeign-provider-service";

    @Autowired
    private ConsulClient consulClient;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void start() {
        if (running.compareAndSet(false, true)) {
            Long index = 0L;
            while (true) {
                ObjectProvider<LoadBalancerCacheManager> cacheManagerProvider = applicationContext.getBeanProvider(LoadBalancerCacheManager.class);
                if (Objects.isNull(cacheManagerProvider.getIfAvailable())) {
                    break;
                }

                LoadBalancerCacheManager loadBalancerCacheManager = cacheManagerProvider.getIfAvailable();
                Cache cache = loadBalancerCacheManager.getCache(CACHE_NAME);
                if (Objects.isNull(cache)) {
                    break;
                }

                QueryParams queryParams = QueryParams.Builder.builder()
                        .setIndex(index)
                        .setConsistencyMode(ConsistencyMode.STALE)
                        .setWaitTime(WAIT_TIME)
                        .build();
                HealthServicesRequest request = HealthServicesRequest.newBuilder()
                        .setQueryParams(queryParams)
                        .setPassing(true)
                        .build();

                long start = System.currentTimeMillis();
                // 查询健康服务列表
                Response<List<HealthService>> response = consulClient.getHealthServices(SERVICE_NAME, request);
                long end = System.currentTimeMillis();
                log.info("get health services cost time：{}", (end - start));

                // 唯一标识当前请求资源状态
                index = response.getConsulIndex();
                // 健康服务列表
                List<HealthService> healthServiceList = response.getValue();
                if (CollectionUtils.isEmpty(healthServiceList)) {
                    continue;
                }

                List<ServiceInstance> serviceInstanceList = new ArrayList<>();
                for (HealthService healthService : healthServiceList) {
                    serviceInstanceList.add(new ConsulServiceInstance(healthService, healthService.getService().getService()));
                }
                cache.put(SERVICE_NAME, serviceInstanceList);
            }
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}

