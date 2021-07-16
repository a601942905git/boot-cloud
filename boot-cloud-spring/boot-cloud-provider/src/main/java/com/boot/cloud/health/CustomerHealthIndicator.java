package com.boot.cloud.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * com.boot.cloud.health.CustomerHealthIndicator
 *
 * @author lipeng
 * @dateTime 2018/11/16 下午4:40
 */
@Component
public class CustomerHealthIndicator implements HealthIndicator {

    public static Status SERVER_STATUS = Status.UP;

    /**
     * 设置服务的健康状态
     * @return
     */
    @Override
    public Health health() {
        if (Objects.equals(SERVER_STATUS, Status.UP)) {
            return new Health.Builder(Status.UP).build();
        } else {
            return new Health.Builder(Status.DOWN).build();
        }
    }
}
