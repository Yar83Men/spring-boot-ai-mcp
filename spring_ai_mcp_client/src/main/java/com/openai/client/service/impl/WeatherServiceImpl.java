package com.openai.client.service.impl;

import com.openai.client.dto.weather.WeatherAIResponse;
import com.openai.client.dto.weather.WeatherRequest;
import com.openai.client.service.WeatherService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class WeatherServiceImpl implements WeatherService {
    private final ChatClient chatClient;
    @Value("classpath:/info/weather.txt")
    private Resource weatherInfo;
    private final Logger LOGGER = LoggerFactory.getLogger(WeatherServiceImpl.class);

    public WeatherServiceImpl(ChatClient.Builder builder, ObjectProvider<ToolCallbackProvider> toolCallbackProviders) {
        final var callbacks = toolCallbackProviders.stream()
                .map(ToolCallbackProvider::getToolCallbacks)
                .flatMap(Arrays::stream)
                .toArray(ToolCallback[]::new);
         this.chatClient = builder
                 .defaultToolCallbacks(callbacks)
                 .build();
    }

    @Override
    public WeatherAIResponse getWeather(@NonNull WeatherRequest request) {
        LOGGER.info("Погода в городе-{}", request.city());
        final var systemMessage = new SystemMessage(weatherInfo);
        final var userMessage = new UserMessage(request.city());

        return chatClient
                .prompt(new Prompt(List.of(systemMessage, userMessage)))
                .call()
                .entity(WeatherAIResponse.class);
    }
}
