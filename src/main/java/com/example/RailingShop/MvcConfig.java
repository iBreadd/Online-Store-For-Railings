package com.example.RailingShop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
public class MvcConfig {

    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/user-details").setViewName("user-details");

        registry.addViewController("/login").setViewName("login");
    }
}
