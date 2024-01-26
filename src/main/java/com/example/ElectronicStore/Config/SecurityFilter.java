package com.example.ElectronicStore.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityFilter {

    private CustomAuthenticationProvider customAuthenticationProvider;

    private JwtAuthFilter jwtAuthFilter;

    public SecurityFilter(CustomAuthenticationProvider customAuthenticationProvider,JwtAuthFilter jwtAuthFilter){
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((csrf)->csrf.disable())
               .authorizeHttpRequests((authorizeRequest)->{
                   authorizeRequest.requestMatchers("/api/v1/auth/register","/api/v1/auth/login")
                           .permitAll()
                           .anyRequest()
                           .authenticated();
               })
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
               .authenticationProvider(customAuthenticationProvider);
       return httpSecurity.build();

    }
}
