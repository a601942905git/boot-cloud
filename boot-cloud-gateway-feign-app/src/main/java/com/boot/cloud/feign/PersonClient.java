package com.boot.cloud.feign;

import com.boot.cloud.PersonApi;
import com.boot.cloud.constant.ServiceNameConst;
import com.boot.cloud.feign.fallback.DefaultFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * com.boot.cloud.feign.PersonClient
 *
 * @author lipeng
 * @date 2019-07-24 13:56
 */
@FeignClient(value = ServiceNameConst.PERSON_SERVICE_NAME, fallbackFactory = DefaultFallbackFactory.class, path = "/feign/persons")
public interface PersonClient extends PersonApi {
}
