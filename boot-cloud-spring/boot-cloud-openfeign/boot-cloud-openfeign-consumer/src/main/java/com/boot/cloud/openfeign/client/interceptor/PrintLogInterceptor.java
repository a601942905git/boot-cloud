package com.boot.cloud.openfeign.client.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * com.boot.cloud.openfeign.client.interceptor.PrintLogInterceptor
 * 定义请求拦截器，拦截器可以使用@Component注解标注，也可以不使用注解标注
 *
 * 拦截器配置：FeignClientFactoryBean的configureUsingProperties()
 * 执行拦截器：SynchronousMethodHandler的executeAndDecode()
 *
 * @see org.springframework.cloud.openfeign.encoding.FeignAcceptGzipEncodingInterceptor
 * @see org.springframework.cloud.openfeign.encoding.FeignContentGzipEncodingInterceptor
 *
 * @author lipeng
 * @date 2021/8/30 7:02 PM
 */
@Slf4j
public class PrintLogInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String requestUrl = template.url();
        String requestBody = new String(template.body(), StandardCharsets.UTF_8);
        log.info("request url：{}，request body：{}", requestUrl, requestBody);
    }
}
