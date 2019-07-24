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
 *
 * @link {https://github.com/spring-cloud/spring-cloud-netflix/issues/2677}
 * @author lipeng
 * @date 2019-07-24 13:56
 */
@FeignClient(value = ServiceNameConst.PERSON_SERVICE_NAME, fallback = DefaultFallback.class)
public interface PersonClient extends PersonApi {
}
