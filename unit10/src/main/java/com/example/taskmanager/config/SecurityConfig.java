package com.example.taskmanager.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.UserDetailsServiceConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean
    public PasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean 
    public UserDetailsService details(PasswordEncoder pascoder) {
        //Spring security interface defines getUsername, getPassword, getAuthorize, some status flags
        UserDetails user = User.builder()
            .username("user")
            .password(pascoder.encode("password"))
            .roles("USER") // Not an enum ;-;
            .build(); //finalizes the object
        
        return new InMemoryUserDetailsManager(user); // associated user manager with in memory database
    }

    @Bean  //define actual security policy, what urls need a login, what is public, what access level do we need?
    // This specific method right now says anyone can hit /login /css/** or /js/** */
    public SecurityFilterChain SFC(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/login", "/css/**","/js/**").permitAll();
                auth.anyRequest().authenticated();
            })
            .formLogin(form -> {
                form.permitAll();
            });

        return http.build();
    }
}