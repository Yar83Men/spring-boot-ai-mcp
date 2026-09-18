package com.openai.client.service.impl;

import com.openai.client.dto.vector_store.VectorStoreAIResponse;
import com.openai.client.service.RegStoreService;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RegStoreServiceImpl implements RegStoreService {
    private final ChatClient chatClient;

    public RegStoreServiceImpl(@Qualifier("vectorStoreChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public VectorStoreAIResponse getFromVectorStore(@NonNull String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .entity(VectorStoreAIResponse.class);
    }
}
