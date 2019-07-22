package com.boot.cloud.feign;

import com.boot.cloud.PersonApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * com.boot.cloud.feign.PersonClient
 *
 * @author lipeng
 * @date 2019-07-22 18:06
 */
@FeignClient("spring-cloud-provider-application")
public interface PersonClient extends PersonApi {

}
