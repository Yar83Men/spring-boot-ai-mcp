package com.openai.start.controller;

import com.openai.start.dto.exchage.ExchangeRequest;
import com.openai.start.dto.exchage.ExchangeResponse;
import com.openai.start.service.ExchangeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
public class ExchangeController {
    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping("/exchange")
    ExchangeResponse exchange(@RequestBody ExchangeRequest request) {
        return exchangeService.exchange(request);
    }
}
