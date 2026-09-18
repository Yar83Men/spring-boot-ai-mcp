package com.openai.client.service.impl;

import com.openai.client.entity.InMemoryChatEntity;
import com.openai.client.service.ChatMemoryService;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ChatAIMemoryServiceImpl implements ChatMemoryService {
    private final ChatClient chatClient;
    private final double chatOptionsTemperature;

    public ChatAIMemoryServiceImpl(ChatClient.Builder builder,
                                   ChatMemory chatMemory,
                                   @Value("${model.chat.options.temperature}") double chatOptionsTemperature) {
        this.chatOptionsTemperature = chatOptionsTemperature;
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(chatMemory)
                        .build())
                .defaultOptions(ChatOptions.builder().temperature(chatOptionsTemperature))
                .build();
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
