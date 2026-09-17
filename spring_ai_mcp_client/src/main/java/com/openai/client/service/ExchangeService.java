package com.openai.client.service;

import com.openai.client.dto.exchage.ExchangeRequest;
import com.openai.client.dto.exchage.ExchangeResponse;

public interface ExchangeService {
    ExchangeResponse exchange(ExchangeRequest request);
}
