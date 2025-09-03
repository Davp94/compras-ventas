package com.blumbit.compras_ventas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blumbit.compras_ventas.auth.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> 
                        req.requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    //DEFINE MORE FILTERS
    // @Bean
    // @Order(2)
    // public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {

    //     http.csrf(AbstractHttpConfigurer::disable)
    //             .authorizeHttpRequests(req -> req.requestMatchers(HttpMethod.POST, "/login").permitAll()
    //                     .requestMatchers(HttpMethod.GET, "/v3/api-docs").permitAll()
    //                     .requestMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()
    //                     .anyRequest()
    //                     .authenticated())
    //             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .authenticationProvider(authenticationProvider)
    //             .addFilterBefore(jwFilter, UsernamePasswordAuthenticationFilter.class);

    //     return http.build();
    // }
}
