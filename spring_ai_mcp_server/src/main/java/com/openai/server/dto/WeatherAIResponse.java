package com.openai.server.dto;

public record WeatherAIResponse(String city, String weatherInfo, String answerMessage) {
}
