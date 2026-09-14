package com.openai.start.service;

import com.openai.start.dto.weather.WeatherAIResponse;
import com.openai.start.dto.weather.WeatherRequest;
import com.openai.start.dto.weather.WeatherResponse;
import org.jspecify.annotations.NonNull;

public interface WeatherService {
    WeatherResponse getWeather(@NonNull WeatherRequest request);

    WeatherAIResponse process(@NonNull WeatherRequest request);
}
