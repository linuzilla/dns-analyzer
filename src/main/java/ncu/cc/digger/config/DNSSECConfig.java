package ncu.cc.digger.config;

import ncu.cc.digger.properties.DNSSECServerProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DNSSECServerProperty.class)
public class DNSSECConfig {
    @Bean
    DNSSECServerProperty dnssecServerProperty(DNSSECServerProperty property) {
        return property;
    }
}
