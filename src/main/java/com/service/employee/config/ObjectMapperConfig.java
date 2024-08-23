package com.service.employee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMapperConfig {

    @Primary
    @Bean
    public ObjectMapper getPrimaryObjectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }
}
