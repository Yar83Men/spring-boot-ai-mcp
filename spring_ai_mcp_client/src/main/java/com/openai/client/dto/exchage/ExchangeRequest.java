package com.openai.client.dto.exchage;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.math.BigDecimal;

public record ExchangeRequest(
        @JsonPropertyDescription("Конвертация с валюты")
        String fromExchange,
        @JsonPropertyDescription("Конвертация в валюту")
        String toExchange,
        @JsonPropertyDescription("Сумма после конвертации валют")
        BigDecimal amountToConvert) {
}
