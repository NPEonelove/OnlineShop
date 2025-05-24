package org.npeonelove.authservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
