package ncu.cc.digger.config;

import ncu.cc.commons.webdev.utils.ClassFinder;
import ncu.cc.commons.webdev.utils.ClassInJarFinderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class MenuConfig {
    @Bean
    public ClassFinder classFinder() {
        return new ClassInJarFinderImpl();
    }
}
