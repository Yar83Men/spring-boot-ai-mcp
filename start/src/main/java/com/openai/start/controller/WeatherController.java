package com.openai.start.controller;

import com.openai.start.dto.weather.WeatherAIResponse;
import com.openai.start.dto.weather.WeatherRequest;
import com.openai.start.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/city")
    @Operation(summary = "Получение погоды в городе", description = "Используются @Tool")
    public WeatherAIResponse getWeather(@NonNull @RequestBody WeatherRequest request) {
        return weatherService.process(request);
    }
}
