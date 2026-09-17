package com.openai.client.controller;

import com.openai.client.dto.weather.WeatherAIResponse;
import com.openai.client.dto.weather.WeatherRequest;
import com.openai.client.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.*;

import static com.openai.client.constant.Constant.DEFAULT_URI;

@RestController
@RequestMapping(DEFAULT_URI)
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/weather/city")
    @Operation(summary = "Получение погоды в городе", description = "Используются @Tool")
    public WeatherAIResponse getWeather(@NonNull @RequestBody WeatherRequest request) {
        return weatherService.getWeather(request);
    }
}
