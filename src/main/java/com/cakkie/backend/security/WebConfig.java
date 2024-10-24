package com.cakkie.backend.security;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map the /images/** URL to the images folder
        registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
    }
}
