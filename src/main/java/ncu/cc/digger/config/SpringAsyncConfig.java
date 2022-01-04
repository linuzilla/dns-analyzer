package ncu.cc.digger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//@Configuration
//@EnableAsync
public class SpringAsyncConfig implements AsyncConfigurer {
//    @Bean
//    protected WebMvcConfigurer webMvcConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//                configurer.setTaskExecutor(getAsyncExecutor());
//            }
//        };
//    }

//    @Bean
//    protected ConcurrentTaskExecutor getTaskExecutor() {
//        return new ConcurrentTaskExecutor(Executors.newFixedThreadPool(5));
//    }

    @Bean
    @Override
    public AsyncTaskExecutor getAsyncExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}