package com.boot.cloud.health;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * com.boot.cloud.health.CustomerHealthCheckHandler
 *
 * @author lipeng
 * @dateTime 2018/11/16 下午5:14
 */
@Component
public class CustomerHealthCheckHandler implements HealthCheckHandler {

    @Autowired
    private CustomerHealthIndicator customerHealthIndicator;

    /**
     * 根据服务的健康状态来决定是否从注册中心移除该服务
     *
     * @param instanceStatus
     * @return
     */
    @Override
    public InstanceInfo.InstanceStatus getStatus(InstanceInfo.InstanceStatus instanceStatus) {
        Status status = customerHealthIndicator.health().getStatus();
        if (Objects.equals(status, Status.UP)) {
            return InstanceInfo.InstanceStatus.UP;
        } else {
            return InstanceInfo.InstanceStatus.DOWN;
        }
    }
}
