package com.boot.cloud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.cloud.RemoteAddressKeyResolverConfig
 *
 * @author lipeng
 * @date 2019-09-18 19:55
 */
@Configuration
public class RemoteAddressKeyResolverConfig {

    @Bean(name = RemoteAddressKeyResolver.BEAN_NAME)
    public RemoteAddressKeyResolver remoteAddressKeyResolver() {
        return new RemoteAddressKeyResolver();
    }
}
