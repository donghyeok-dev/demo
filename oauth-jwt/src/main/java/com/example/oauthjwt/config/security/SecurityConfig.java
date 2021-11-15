package com.example.oauthjwt.config.security;

import com.example.oauthjwt.config.oauth2.enums.RoleType;
import com.example.oauthjwt.config.oauth2.service.CustomOAuthUserService;
import com.example.oauthjwt.config.oauth2.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuthUserService customOAuthUserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final CorsProperties corsProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                    .cors()
                .and()
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .exceptionHandling()
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/**").hasAnyAuthority(RoleType.USER.getCode())
                    .anyRequest()
                    .authenticated()
                .and()
                    .oauth2Login()
                    .authorizationEndpoint()
                    .baseUri("/oauth2/authorization")
                    .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                    .userInfoEndpoint()
                    .userService(customOAuthUserService)
                .and()
                    .successHandler(oAuth2SuccessHandler);

    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> oAuth2AuthorizationRequestBasedOnCookieRepository() {
//        return new HttpSessionOAuth2AuthorizationRequestRepository(); // session 사용시
        return new OAuth2AuthorizationRequestBasedOnCookieRepository(); // jwt cookie 사용시
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}
