package ncu.cc.digger.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

@Configuration
public class MessageSourceConfig {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasenames("lang/messages");
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;

//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasenames("classpath:/lang/messages");
        messageSource.setBasenames(
                "lang/messages",
                "lang/validation",
                "lang/menu",
                "lang/chinese"
        );
        // messageSource.setCacheSeconds(60); // reload messages every 60 seconds
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());

        return messageSource;
    }
}
