package com.example.RailingShop;

import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

//    @Bean
//    public UserDetailsService employeeDetailsService(){return new EmployeeDetailsServiceImpl();}

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.authorizeHttpRequests((request)-> request

                .requestMatchers("/registration","/register/employee",
                        "/login/user", "/login/employee","/employee/menu",
                        "/products/**","/products/add","/").permitAll()
                        .requestMatchers("/user-details").authenticated()
                        .requestMatchers("/menu").permitAll()

        ).formLogin((form) -> form
                .loginPage("/login").permitAll()
                )
                .logout((logout) -> logout.clearAuthentication(true).logoutUrl("/logout"));


        return http.build();
    }
}
