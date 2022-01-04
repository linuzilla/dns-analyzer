package ncu.cc.digger.config;

import ncu.cc.digger.constants.Routes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

//@Configuration
//@EnableWebFluxSecurity
public class SecurityConfig {
//    @Bean
//    ReactiveUserDetailsService reactiveUserDetailsService() {
//        UserDetails annie = User.withUsername("annie").roles("USER").password("{noop}pass").build();
//        UserDetails elsa = User.withUsername("elsa").roles("USER", "ADMIN").password("{noop}pass").build();
//
//        UserDetails mac = User.withDefaultPasswordEncoder().username("mac").password("pass").roles("USER").build();
//
//        return new MapReactiveUserDetailsService(annie, elsa, mac);
//    }
//
//    @Bean
//    SecurityWebFilterChain security(ServerHttpSecurity serverHttpSecurity) {
//        return serverHttpSecurity
////                .httpBasic()
////                .and()
//                .authorizeExchange()
////                .pathMatchers(Routes.ACTUATOR + "/**").authenticated()
//                .pathMatchers(Routes.ROOT, Routes.RESOURCES + "/**", Routes.WEBJARS + "/**").permitAll()
//                .pathMatchers(Routes.LOGIN + "/**", Routes.SIGNUP + "/**").permitAll()
//                .pathMatchers(Routes.ABOUT + "/**").permitAll()
//                .pathMatchers(Routes.EXPIRED + "/**").permitAll()
//                .pathMatchers(Routes.ERROR + "/**").permitAll()
//                .pathMatchers(Routes.FAVICON).permitAll()
//                .pathMatchers(Routes.LANG).permitAll()
//                .pathMatchers(Routes.FLASH + "/**").permitAll()
//                .pathMatchers(Routes.ZONE + "/**").permitAll()
//                .pathMatchers(Routes.QUERY + "/**").permitAll()
//                .pathMatchers(Routes.BACKEND + Routes.BACKEND_MENU).permitAll()
////                .pathMatchers(Routes.ACTUATOR + "/**").access((mono, context) -> {
////
////                            InetSocketAddress remoteAddress = context.getExchange().getRequest().getRemoteAddress();
////
////                            if ("127.0.0.1".equals(remoteAddress.getHostName())) {
////                                return Mono.just(new AuthorizationDecision(true));
////                            } else {
////                                return Mono.just(new AuthorizationDecision(false));
////                            }
////                        }
////                )
////
////                    return mono
////                        .doOnNext(authentication -> StackTraceUtil.print1("here"))
////                        .map(auth -> context.getExchange().getRequest().getRemoteAddress())
////                        .map(InetSocketAddress::getAddress)
////                        .doOnNext(StackTraceUtil::print1)
////                        .filter(Objects::nonNull)
////                        .map(InetAddress::getHostAddress)
////                        .doOnNext(StackTraceUtil::print1)
////                        .map("127.0.0.1"::equals)
////                        .map(AuthorizationDecision::new); })
//                .anyExchange().authenticated()
//                .and()
//                .build();
//    }
}
