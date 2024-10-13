package com.project01.quiz.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Configuration
    public class WebConfigs implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            // Cấu hình để Spring phục vụ file từ thư mục uploads/avatar/
            registry.addResourceHandler("/avatar/**")
                    .addResourceLocations("file:uploads/avatar/");
        }
    }
}
