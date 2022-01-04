package ncu.cc.digger.config;

import ncu.cc.digger.constants.BeanIds;
import ncu.cc.digger.constants.RolesEnum;
import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.security.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.BeanIds.AUTHENTICATION_MANAGER;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@EnableWebSecurity
public class MultiHttpSecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(MultiHttpSecurityConfig.class);
    private static final String REALM = "DNS-Analyzer";


    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher(Routes.API_PATH + "/**")
                    .authorizeRequests()
                    .anyRequest().hasRole(RolesEnum.APICLIENT)
                    .and()
                    .csrf().disable()
                    .httpBasic().realmName(REALM)
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        ;
    }

    @Configuration
    @Order(2)
    public static class PublicApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher(Routes.PUBLIC_API_PATH + "/**")
                    .authorizeRequests()
                    .anyRequest().permitAll()
                    .and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Configuration
    public static class SecurityConfigure extends WebSecurityConfigurerAdapter {
        private final SessionRegistry sessionRegistry;
        private final PasswordEncoder passwordEncoder;
        private final UserDetailsService userDetailsService;
        private final AuthenticationService authenticationService;

        public SecurityConfigure(SessionRegistry sessionRegistry,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationService authenticationService,
                                 @Qualifier(BeanIds.USER_DETAILS_SERVICE_BEAN)
                                         UserDetailsService userDetailsService) {
            this.sessionRegistry = sessionRegistry;
            this.passwordEncoder = passwordEncoder;
            this.userDetailsService = userDetailsService;
            this.authenticationService = authenticationService;
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider
                    = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService);
            authProvider.setPasswordEncoder(passwordEncoder);
            return authProvider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                    .authorizeRequests()
                    .antMatchers(Routes.ROOT, Routes.RESOURCES + "/**", Routes.WEBJARS + "/**").permitAll()
                    .antMatchers(Routes.SIGNUP + "/**").permitAll()
                    .antMatchers(Routes.ABOUT + "/**").permitAll()
                    .antMatchers(Routes.ROBOTS).permitAll()
                    .antMatchers(Routes.FAVICON).permitAll()
                    .antMatchers(Routes.LANG).permitAll()
                    .antMatchers(Routes.FLASH + "/**").permitAll()
                    .antMatchers(Routes.QUERY + "/**").permitAll()
                    .antMatchers(Routes.ZONE + "/**").permitAll()
                    .antMatchers(Routes.STATISTICS + "/**").permitAll()
                    .antMatchers(Routes.UNIV + "/**").permitAll()
                    .antMatchers(Routes.BACKEND + Routes.BACKEND_MENU).permitAll()
                    .antMatchers(Routes.BACKEND + Routes.BACKEND_COUNTRY).permitAll()
                    .antMatchers(Routes.REPORT + "/**").hasRole(RolesEnum.USER)
                    .antMatchers(Routes.VIEW + "/**").hasRole(RolesEnum.VIEW)
                    .antMatchers(Routes.MANAGE + "/**").hasRole(RolesEnum.ADMIN)
                    .antMatchers(Routes.ACTUATOR + "/**").access("hasRole('DEVELOPER') or hasIpAddress('127.0.0.1')")
                    .antMatchers(Routes.DEVEL + "/**").hasRole(RolesEnum.DEVELOPER)
                    .anyRequest().authenticated()
                    .and().oauth2Login()
                    .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and().exceptionHandling()
                    .and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .sessionFixation().migrateSession()
                    .maximumSessions(1).maxSessionsPreventsLogin(false);

            // @formatter:on

        }

        @Bean(name = AUTHENTICATION_MANAGER)
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }
}