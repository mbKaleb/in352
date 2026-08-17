package com.example.taskmanager.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean
    public PasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean  //define actual security policy, what urls need a login, what is public, what access level do we need?
    // This specific method right now says anyone can hit /login /css/** or /js/** */
    public SecurityFilterChain SFC(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/", "/register", "/login", "/css/**","/js/**").permitAll();
                auth.anyRequest().authenticated();
            })
            .formLogin(form -> {
                form.loginPage("/login").permitAll()
                    .defaultSuccessUrl("/dashboard", true);
            });

        return http.build();
    }
}