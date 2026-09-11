package com.openai.start.service;

import com.openai.start.dto.WeatherRequest;
import com.openai.start.dto.WeatherResponse;
import org.jspecify.annotations.NonNull;

public interface WeatherService {
    WeatherResponse getWeather(@NonNull WeatherRequest request);

    String process(@NonNull WeatherRequest request);
}
