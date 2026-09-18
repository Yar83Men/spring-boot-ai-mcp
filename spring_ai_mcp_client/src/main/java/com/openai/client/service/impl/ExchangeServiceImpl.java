package com.openai.client.service.impl;

import com.openai.client.dto.exchage.ExchangeRequest;
import com.openai.client.dto.exchage.ExchangeResponse;
import com.openai.client.service.ExchangeService;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    private final ChatClient chatClient;
    private final double chatOptionsTemperature;

    public ExchangeServiceImpl(ChatClient.Builder builder,
                               ObjectProvider<ToolCallbackProvider> toolCallbackProviders,
                               @Value("${model.chat.options.temperature}") double chatOptionsTemperature) {
        this.chatOptionsTemperature = chatOptionsTemperature;
        final var callbacks = toolCallbackProviders.stream()
                .map(ToolCallbackProvider::getToolCallbacks)
                .flatMap(Arrays::stream)
                .toArray(ToolCallback[]::new);
        this.chatClient = builder
                .defaultOptions(ChatOptions.builder().temperature(chatOptionsTemperature))
                .defaultTools((Object[]) callbacks)
                .build();
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
