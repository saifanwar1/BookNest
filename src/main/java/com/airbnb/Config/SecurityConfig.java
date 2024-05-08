package com.airbnb.Config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;



@Configuration
public class SecurityConfig{
    private JWTRequestFilter jwtRequestFilter;

    public SecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtRequestFilter,AuthorizationFilter.class);
        http.authorizeHttpRequests()
              .requestMatchers("/api/abb/addUser","/api/abb/Login")
                .permitAll()
                .requestMatchers("api/v1/countries/addcountry").hasRole("ADMIN")
              //  .requestMatchers("api/abb/profile").hasAnyRole("ADMIN","USER")
              .anyRequest().authenticated();

        return http.build();
    }
}
