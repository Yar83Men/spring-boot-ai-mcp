package com.openai.client.dto.exchage;

import java.math.BigDecimal;

public record ExchangeRequest(String fromExchange, String toExchange, BigDecimal amountToConvert) {
}
