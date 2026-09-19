package com.openai.client.controller;

import com.openai.client.dto.exchage.ExchangeRequest;
import com.openai.client.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.openai.client.constant.Constant.DEFAULT_URI;

@RestController
@RequestMapping(DEFAULT_URI)
public class ExchangeController {
    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping("/exchange")
    public ResponseEntity<?> exchange(@RequestBody ExchangeRequest request) {
        return ResponseEntity.ok(exchangeService.exchange(request));
    }
}
