package org.npeonelove.profileservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfileConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
