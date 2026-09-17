package com.openai.client.service;

import com.openai.client.dto.weather.WeatherAIResponse;
import com.openai.client.dto.weather.WeatherRequest;
import com.openai.client.dto.weather.WeatherResponse;
import org.jspecify.annotations.NonNull;

public interface WeatherService {
    WeatherResponse getWeather(@NonNull WeatherRequest request);

    WeatherAIResponse process(@NonNull WeatherRequest request);
}
