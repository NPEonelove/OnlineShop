package org.npeonelove.catalogservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
