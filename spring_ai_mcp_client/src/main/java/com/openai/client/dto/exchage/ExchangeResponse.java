package com.openai.client.dto.exchage;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.math.BigDecimal;
import java.time.LocalDate;


public record ExchangeResponse(String resultMessage,
                               BigDecimal resultExchange,
                               LocalDate onDate,
                               @JsonPropertyDescription("Коэффициент соотношения валют")
                               BigDecimal coefficient) {
}
