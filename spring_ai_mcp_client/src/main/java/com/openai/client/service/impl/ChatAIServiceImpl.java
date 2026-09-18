package com.openai.client.service.impl;

import com.openai.client.service.ChatAIService;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatAIServiceImpl implements ChatAIService {
    private final ChatClient chatClient;
    private final double chatOptionsTemperature;

    public ChatAIServiceImpl(ChatClient.Builder builder,
                             @Value("${model.chat.options.temperature}") double chatOptionsTemperature) {
        this.chatClient = builder
                .defaultOptions(ChatOptions.builder().temperature(chatOptionsTemperature))
                .build();
        this.chatOptionsTemperature = chatOptionsTemperature;
    }

    @Override
    public String answer(@NonNull String question) {
        return this.chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    @Override
    public ChatResponse getAIDetails(@NonNull String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .chatResponse();
    }
}
