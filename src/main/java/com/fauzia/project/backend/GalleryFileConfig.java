package com.fauzia.project.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GalleryFileConfig implements WebMvcConfigurer {
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/resources/photo/**")
                .addResourceLocations("file:///D:/Upload/");
    }
}
