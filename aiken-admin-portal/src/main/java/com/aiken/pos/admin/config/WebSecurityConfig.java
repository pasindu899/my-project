package com.aiken.pos.admin.config;

import com.aiken.pos.admin.config.filter.ApiSessionFilter;
import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.exception.LoggingAccessDeniedHandler;
import com.aiken.pos.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Application Security Manager
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-21
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomLoginFailureHandler loginFailureHandler;

    @Autowired
    private CustomLoginSuccessHandler loginSuccessHandler;

    @Autowired
    private CustomLogoutSuccessHandler logOutSuccessHandler;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().ignoringAntMatchers(Endpoint.URL_API_V1 + "/**").and()
                .authorizeRequests()
                .antMatchers(
                        "/forgot-password**").permitAll()
                .antMatchers("/", "/js/**", "/css/**", "/img/**", "/images/**", "/webjars/**").permitAll()
                .antMatchers(Endpoint.URL_API_V1 + "/**").hasRole(UserRoleMapper.ROLE_POS_USER)
                .anyRequest()
                .authenticated()
                .and()
                .authenticationProvider(authenticationProvider())
                .httpBasic(httpBasicConfigurer -> httpBasicConfigurer
                        .authenticationEntryPoint(customAuthenticationEntryPoint))
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler)
                .permitAll()
                .and().logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .logoutSuccessHandler(logOutSuccessHandler)
                .permitAll().and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .addFilterBefore(new ApiSessionFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public CustomDaoAuthenticationProvider authenticationProvider() {
        CustomDaoAuthenticationProvider customDaoAuthenticationProvider = new CustomDaoAuthenticationProvider(userService);
        customDaoAuthenticationProvider.setUserDetailsService(userDetailsService);
        customDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return customDaoAuthenticationProvider;
    }
}