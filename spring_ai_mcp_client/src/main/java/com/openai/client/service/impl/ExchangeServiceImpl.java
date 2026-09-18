package com.openai.client.service.impl;

import com.openai.client.dto.exchage.ExchangeRequest;
import com.openai.client.dto.exchage.ExchangeResponse;
import com.openai.client.service.ExchangeService;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class ExchangeServiceImpl implements ExchangeService {
    private final ChatClient chatClient;

    public ExchangeServiceImpl(@Qualifier("toolBacksChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public ExchangeResponse exchange(@NonNull ExchangeRequest request) {
        final var userMessage = new UserMessage(String.format("Конвертируй с валюты %s на валюту %s количество %s, посчитай коэффициент соотношения курсов валют",
                request.fromExchange(),
                request.toExchange(),
                request.amountToConvert().toString()));
        return chatClient
                .prompt(new Prompt(userMessage))
                .call()
                .entity(ExchangeResponse.class);
    }
}
