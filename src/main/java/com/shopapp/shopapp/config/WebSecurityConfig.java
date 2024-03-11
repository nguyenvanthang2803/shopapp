package com.shopapp.shopapp.config;

import com.shopapp.shopapp.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (request)-> request
                                .requestMatchers("api/v1/users/register"
                                        ,"api/v1/users/login").permitAll()
                                .requestMatchers(POST,"api/v1/orders/**").hasRole("USER")
                                .requestMatchers(GET,"api/v1/orders/**").hasAnyRole("ADMIN","USER")
                                .requestMatchers(PUT,"api/v1/orders/**").hasRole("ADMIN")
                                .requestMatchers(DELETE,"api/v1/orders/**").hasRole("ADMIN")

                                .anyRequest().authenticated())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
