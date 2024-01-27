package com.example.ElectronicStore.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private AuthenticationManagerBuilder authenticationManagerBuilder;

    public SecurityConfig(AuthenticationManagerBuilder authenticationManagerBuilder){
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Bean
    public AuthenticationManager authManager(CustomAuthenticationProvider customAuthenticationProvider){
        return authentication -> customAuthenticationProvider.authenticate(authentication);
    }

}
