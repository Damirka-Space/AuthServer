package com.damirka.authserver.config;

import com.damirka.authserver.entities.RoleEnum;
import com.damirka.authserver.security.DefaultUserDetailsService;
import com.damirka.authserver.security.oauth2.CustomOAuth2UserService;
import com.damirka.authserver.security.oauth2.CustomOidcUserService;
import com.damirka.authserver.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class DefaultSecurityConfig {

    private final DefaultUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oauthUserService;
    private final CustomOidcUserService oidcUserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").hasAuthority("SCOPE_profile")
                        .requestMatchers("/login/**", "/registration/**", "/logout").permitAll()
                        .requestMatchers("/admin/**").hasAuthority(RoleEnum.ADMIN.toString())
                        .anyRequest().authenticated()
                )
                // Accept access tokens for User Info and/or Client Registration
                .oauth2ResourceServer(resourceServerConfigurer ->
                        resourceServerConfigurer.opaqueToken(Customizer.withDefaults()))
                .userDetailsService(userDetailsService)
                .formLogin(login -> login.loginPage("/login"))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2")
                            .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                    .userService(oauthUserService)
                                    .oidcUserService(oidcUserService)).successHandler(oAuth2AuthenticationSuccessHandler));

        return http.build();
    }

    @Bean
    UserDetailsService users() {
        return userDetailsService;
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

}
