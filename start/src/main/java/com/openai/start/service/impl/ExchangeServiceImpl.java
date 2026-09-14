package com.openai.start.service.impl;

import com.openai.start.dto.exchage.ExchangeRequest;
import com.openai.start.dto.exchage.ExchangeResponse;
import com.openai.start.service.ExchangeService;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    private final ChatClient chatClient;

    public ExchangeServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public ExchangeResponse exchange(@NonNull ExchangeRequest request) {
        final var userMessage = new UserMessage(String.format("Конвертируй с валюты %s на валюту %s количество %s",
                request.fromExchange(),
                request.toExchange(),
                request.amountToConvert().toString()));
        return chatClient
                .prompt(new Prompt(userMessage))
                .call()
                .entity(ExchangeResponse.class);
    }
}
