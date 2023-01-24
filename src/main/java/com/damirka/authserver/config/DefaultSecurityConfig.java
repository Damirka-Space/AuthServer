package com.damirka.authserver.config;

import com.damirka.authserver.entities.RoleEnum;
import com.damirka.authserver.security.DefaultUserDetailsService;
import com.damirka.authserver.security.oauth2.CustomOAuth2UserService;
import com.damirka.authserver.security.oauth2.CustomOidcUserService;
import com.damirka.authserver.security.oauth2.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DefaultSecurityConfig {

    private final DefaultUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oauthUserService;
    private final CustomOidcUserService oidcUserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    DefaultSecurityConfig(DefaultUserDetailsService userDetailsService, CustomOAuth2UserService oauthUserService,
                          OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler, CustomOidcUserService oidcUserService) {
        this.userDetailsService = userDetailsService;
        this.oauthUserService = oauthUserService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oidcUserService = oidcUserService;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority(RoleEnum.ADMIN.toString())
//                        .requestMatchers("/user/**").hasAuthority("SCOPE_user.profile")
                        .anyRequest().authenticated()
                ).userDetailsService(userDetailsService)
                .formLogin().loginPage("/auth/login")
                .and()
                .oauth2Login().loginPage("/oauth2")
                .userInfoEndpoint().userService(oauthUserService)
                        .oidcUserService(oidcUserService)
                .and().successHandler(oAuth2AuthenticationSuccessHandler);


//        http.authorizeRequests(authorizeRequests -> {
//                    authorizeRequests
//                            .antMatchers("/login/**", "/registration/**", "/logout").permitAll()
//                            .antMatchers("/admin/**").hasAuthority(RoleEnum.ADMIN.toString())
//                            .anyRequest().authenticated();
//                }
//                ).userDetailsService(userDetailsService)
//                .formLogin().loginPage("/login")
//                .and()
//                .oauth2Login().loginPage("/oauth2")
//                    .userInfoEndpoint().userService(oauthUserService)
////                        .oidcUserService(oidcUserService)
//                .and().successHandler(oAuth2AuthenticationSuccessHandler);

        http.logout().logoutUrl("/logout");


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
