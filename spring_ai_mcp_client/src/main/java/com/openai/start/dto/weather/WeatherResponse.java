package com.openai.start.dto.weather;

public record WeatherResponse(
        String city,
        int temperatureCelsius,
        int humidity,
        int pressure,
        int uvIndex,
        String observationTime) {
}
