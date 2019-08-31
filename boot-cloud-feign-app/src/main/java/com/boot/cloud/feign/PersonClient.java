package com.boot.cloud.feign;

import com.boot.cloud.PersonApi;
import com.boot.cloud.constant.ServiceNameConst;
import com.boot.cloud.feign.fallback.DefaultFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * com.boot.cloud.feign.PersonClient
 *
 * 针对降级处理，建议使用fallbackFactory，由于使用fallback可能导致RequestMapping映射冲突，需要特殊手段来解决，
 * fallbackFactory可以获取异常信息
 * @link {https://github.com/spring-cloud/spring-cloud-netflix/issues/2677}
 *
 * 如果指定FeignClient的configuration属性，那么配置类不需要指定@Configuration注解，否则该配置会成为全局配置
 * 原理：每个@FeignClient注解都会对应一个FeignClientsConfiguration配置，
 * 如果自定义的配置使用@Configuration标注，那么在父容器中就会存在FeignClient的配置
 * 每个FeignClient在加载FeignClientsConfiguration配置的时候发现父容器已经存在该配置，就不会加载属于自己的配置，从而使用全局配置
 *
 * @author lipeng
 * @date 2019-07-24 13:56
 */
@FeignClient(value = ServiceNameConst.PERSON_SERVICE_NAME, fallback = DefaultFallback.class)
public interface PersonClient extends PersonApi {
}
