package com.openai.server.service;

import com.openai.server.dto.WeatherRequest;
import com.openai.server.dto.WeatherResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
public class WeatherApiToolService {
    private final RestClient restClient;
    private final String HTTPS = "https";
    private final String weatherAPIHost;
    private final String weatherApiPath;
    private final String weatherApiKey;

    public WeatherApiToolService(@Value("${weather.api-url}") String weatherAPIHost,
                                 @Value("${weather.api-path}") String weatherApiPath,
                                 @Value("${weather.api-key}") String weatherApiKey) {
        this.weatherAPIHost = weatherAPIHost;
        this.weatherApiPath = weatherApiPath;
        this.weatherApiKey = weatherApiKey;
        this.restClient = RestClient.create();
    }

    @Tool(description = "Получить погоду в указанном городе")
    public WeatherResponse weatherFromApi(@ToolParam(description = "Название города для получения погоды")
                                          @NonNull WeatherRequest request) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme(HTTPS)
                        .host(weatherAPIHost)
                        .path(weatherApiPath)
                        .queryParam("q", request.city())
                        .queryParam("appid", weatherApiKey)
                        .queryParam("units", "metric")
                        .queryParam("lang", "ru")
                        .build())
                .retrieve()
                .body(WeatherResponse.class);
    }
}
