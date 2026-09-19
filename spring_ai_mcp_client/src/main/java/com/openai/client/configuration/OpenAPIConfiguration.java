package com.openai.client.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .description("API для взаимодействия с OpenAI Chat-GPT, а также с MCP серверами")
                        .version("1.0.11")
                        .title("Тестовое API на Spring boot 4 и Spring AI 2, для работы с AI агентом"));
    }
}
