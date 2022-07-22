package com.damirka.authserver.config;

import com.damirka.authserver.security.DefaultUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class DefaultSecurityConfig {

    private DefaultUserDetailsService userDetailsService;

    @Autowired
    DefaultSecurityConfig(DefaultUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/login/**", "/registration/**", "/logout").permitAll()
                                .anyRequest().authenticated()
                ).formLogin().loginPage("/login");

        http.logout().logoutUrl("/logout");

        http.userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    UserDetailsService users() {
        return userDetailsService;
    }

}
