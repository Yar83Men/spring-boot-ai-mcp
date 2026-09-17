package com.openai.start.dto.exchage;

import java.math.BigDecimal;

public record ExchangeRequest(String fromExchange, String toExchange, BigDecimal amountToConvert) {
}
