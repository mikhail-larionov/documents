package com.example.documents.configuration;

import com.example.documents.generator.templateResolver.TemplateResolver;
import com.example.documents.model.enums.TemplateType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class DocumentConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public Map<TemplateType, TemplateResolver> templateResolverMap(List<TemplateResolver> templateResolvers) {
        return templateResolvers.stream().collect(Collectors.toMap(TemplateResolver::getTemplateType, Function.identity()));
    }
}
