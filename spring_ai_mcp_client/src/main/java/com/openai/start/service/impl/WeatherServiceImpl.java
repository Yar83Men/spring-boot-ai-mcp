package com.openai.start.service.impl;

import com.openai.start.dto.weather.WeatherAIResponse;
import com.openai.start.dto.weather.WeatherRequest;
import com.openai.start.dto.weather.WeatherResponse;
import com.openai.start.service.WeatherService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.List;


@Service
public class WeatherServiceImpl implements WeatherService {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final ChatClient chatClient;
    @Value("classpath:/info/weather.txt")
    private Resource weatherInfo;
    private final Logger LOGGER = LoggerFactory.getLogger(WeatherServiceImpl.class);
    private final String CURRENT_CONDITION = "current_condition";
    private final String FORMAT_REQUEST = "{}?format=j2&lang=ru";
    private final String API_URL = "https://wttr.in/";
    private final String TEMP_C = "temp_C";
    private final String HUMIDITY = "humidity";
    private final String PRESSURE = "pressure";
    private final String UV_INDEX = "uvIndex";
    private final String OBSERVATION_TIME = "observation_time";
    private final int DEFAULT_VALUE = 0;

    public WeatherServiceImpl(ObjectMapper objectMapper, ChatClient.Builder builder) {
        this.objectMapper = objectMapper;
        this.chatClient = builder.build();
        this.restClient = RestClient.create(API_URL);
    }

    @Override
    @Tool(description = "Получить погоду в указанном городе")
    public WeatherResponse getWeather(@NonNull WeatherRequest request) {
        final var response = restClient.get().uri(FORMAT_REQUEST, request.city()).retrieve().body(String.class);
        final var city = request.city();
        try {
            final var tempC = getIntValue(response, TEMP_C);
            final var humidity = getIntValue(response, HUMIDITY);
            final var pressure = getIntValue(response, PRESSURE);
            final var uvIndex = getIntValue(response, UV_INDEX);
            final var observationTime = getStringValue(response, OBSERVATION_TIME);
            return new WeatherResponse(city, tempC, humidity, pressure, uvIndex, observationTime);

        } catch (Exception ex) {
            LOGGER.error("Ошибка получения данных о погоде");
            return new WeatherResponse(city, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, null);
        }
    }

    @Override
    public WeatherAIResponse process(@NonNull WeatherRequest request) {
        LOGGER.info("Погода в городе-{}", request);
        final var systemMessage = new SystemMessage(weatherInfo);
        final var userMessage = new UserMessage(request.city());

        return chatClient
                .prompt(new Prompt(List.of(systemMessage, userMessage)))
                .tools(this)
                .call()
                .entity(WeatherAIResponse.class);
    }

    private int getIntValue(String response, String parameter) {
        return objectMapper
                .readTree(response)
                .path(CURRENT_CONDITION)
                .path(0)
                .path(parameter)
                .asInt();
    }

    private String getStringValue(String response, String parameter) {
        return objectMapper
                .readTree(response)
                .path(CURRENT_CONDITION)
                .path(0)
                .path(parameter)
                .asString();
    }
}
