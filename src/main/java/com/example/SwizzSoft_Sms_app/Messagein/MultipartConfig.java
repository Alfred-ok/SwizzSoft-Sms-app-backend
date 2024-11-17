package com.example.SwizzSoft_Sms_app.Messagein;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class MultipartConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(10)); // Adjust max file size
        factory.setMaxRequestSize(DataSize.ofMegabytes(20)); // Adjust max request size
        return factory.createMultipartConfig();
    }
}
