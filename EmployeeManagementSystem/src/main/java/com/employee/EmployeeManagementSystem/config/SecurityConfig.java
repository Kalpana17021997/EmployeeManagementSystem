package com.employee.EmployeeManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login","/register").permitAll()  // Allow public access to /public/**
                                .anyRequest().authenticated()  // All other requests require authentication
                )
                .formLogin(withDefaults())  // Enable form login
                .httpBasic(withDefaults()); // Enable HTTP basic authentication

        return http.build();  // Build and return the SecurityFilterChain
    }

    @Bean
    public UserDetailsService users(){
        UserDetails admin = User.withUsername("admin").password("{noop}admin").roles("USER").build();
        return new InMemoryUserDetailsManager(admin);
    }
}
