package com.openai.start.service.impl;

import com.openai.start.service.ChatAIService;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class ChatAIServiceImpl implements ChatAIService {
    private final ChatClient chatClient;

    public ChatAIServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
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
