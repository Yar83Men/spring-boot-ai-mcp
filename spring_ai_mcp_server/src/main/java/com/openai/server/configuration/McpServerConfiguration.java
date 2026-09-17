package com.openai.server.configuration;

import com.openai.server.service.WeatherApiToolService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpServerConfiguration {

    @Bean
    public ToolCallbackProvider weatherToolCallbackProvider(WeatherApiToolService weatherApiToolService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherApiToolService)
                .build();
    }
}
