package com.openai.start.dto.exchage;

import java.math.BigDecimal;
import java.time.LocalDate;


public record ExchangeResponse(String resultMessage, BigDecimal resultExchange, LocalDate onDate) {
}
