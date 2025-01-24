package com.project01.quiz.config;


import com.cloudinary.Cloudinary;
import lombok.experimental.NonFinal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebConfig {

    @Value("${LEGEND.CLOUD_NAME}")
    @NonFinal
    private String CLOUD_NAME;

    @Value("${LEGEND.API_KEY}")
    @NonFinal
    private String API_KEY;

    @Value("${LEGEND.API_SECRET}")
    @NonFinal
    private String API_SECRET;


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

    @Bean
    public Cloudinary cloudinary() {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);
        return new Cloudinary(config);
    }


}
