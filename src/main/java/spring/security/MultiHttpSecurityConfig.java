package spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@ComponentScan("spring")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MultiHttpSecurityConfig {

    private final AuthenticationService authenticationService;

    private static CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private static AuthenticationEntryPointImpl authenticationEntryPointImpl;

    private static MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler;

    private static SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Autowired
    public MultiHttpSecurityConfig(AuthenticationService authenticationService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, AuthenticationEntryPointImpl authenticationEntryPointImpl, MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler) {
        this.authenticationService = authenticationService;
        MultiHttpSecurityConfig.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        MultiHttpSecurityConfig.authenticationEntryPointImpl = authenticationEntryPointImpl;
        MultiHttpSecurityConfig.mySuccessHandler = mySuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            CharacterEncodingFilter filter = new CharacterEncodingFilter();
            filter.setEncoding("UTF-8");
            filter.setForceEncoding(true);
            http.csrf().disable().addFilterBefore(filter, CsrfFilter.class);
            http.authorizeRequests()
                    .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
                    .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .successHandler(customAuthenticationSuccessHandler)
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .and()
                    .logout().logoutSuccessUrl("/login?logout");
        }
    }

    @Configuration
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .antMatcher("/api/users/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("RESTCLIENT")
                    .and()
                    .httpBasic()
                    .authenticationEntryPoint(authenticationEntryPointImpl)
                    .and()
                    .formLogin()
                    .successHandler(mySuccessHandler)
                    .failureHandler(myFailureHandler)
                    .and()
                    .logout();
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(passwordEncoder());
    }
}