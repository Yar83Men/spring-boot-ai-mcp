package com.openai.start.service;

import com.openai.start.dto.exchage.ExchangeRequest;
import com.openai.start.dto.exchage.ExchangeResponse;

public interface ExchangeService {
    ExchangeResponse exchange(ExchangeRequest request);
}
