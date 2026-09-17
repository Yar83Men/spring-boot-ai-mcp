package com.openai.client.service;

import com.openai.client.dto.weather.WeatherAIResponse;
import com.openai.client.dto.weather.WeatherRequest;
import org.jspecify.annotations.NonNull;

public interface WeatherService {
    WeatherAIResponse getWeather(@NonNull WeatherRequest request);
}
