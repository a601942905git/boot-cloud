package com.boot.cloud.health;

import org.springframework.boot.actuate.health.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * com.boot.cloud.health.HealthController
 *
 * @author lipeng
 * @dateTime 2018/11/16 下午4:49
 */
@RestController
@RequestMapping("/server")
public class HealthController {

    public static final String SERVER_DOWN = "down";

    public static final String SERVER_UP = "up";

    @GetMapping("/{status}")
    public void operateServerHealth(@PathVariable String status) {
        if (Objects.equals(status, SERVER_UP)) {
            CustomerHealthIndicator.SERVER_STATUS = Status.UP;
        } else {
            CustomerHealthIndicator.SERVER_STATUS = Status.DOWN;
        }
    }
}
