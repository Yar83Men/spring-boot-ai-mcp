package com.openai.client.service.impl;

import com.openai.client.entity.InMemoryChatEntity;
import com.openai.client.service.ChatMemoryService;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class ChatAIMemoryServiceImpl implements ChatMemoryService {
    private final ChatClient chatClient;

    public ChatAIMemoryServiceImpl(@Qualifier("memoryChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public InMemoryChatEntity chatWithMemory(@NonNull String message, @NonNull String conversationId) {
        final var systemMessage = new SystemMessage("В ответе в поле conversationId=" + conversationId);
        final var prompt = new Prompt(systemMessage);
        return chatClient.prompt(prompt)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .user(message)
                .call()
                .entity(InMemoryChatEntity.class);
    }
}
