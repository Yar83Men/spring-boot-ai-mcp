package com.openai.client.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ChatClientCustomConfiguration {
    private final double chatOptionsTemperature;
    private final int topK;
    private final double similarityThreshold;

    public ChatClientCustomConfiguration(@Value("${model.chat.options.temperature}")
                                         double chatOptionsTemperature,
                                         @Value("${vector-store.top-k}") int topK,
                                         @Value("${vector-store.similarity-threshold}") double similarityThreshold) {
        this.chatOptionsTemperature = chatOptionsTemperature;
        this.topK = topK;
        this.similarityThreshold = similarityThreshold;
    }

    @Bean
    public ChatClient defaultChatClient(ChatClient.Builder builder) {
        return builder
                .defaultOptions(ChatOptions.builder().temperature(chatOptionsTemperature))
                .build();
    }

    @Bean
    public ChatClient memoryChatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
        return builder
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(chatMemory)
                        .build())
                .defaultOptions(ChatOptions.builder().temperature(chatOptionsTemperature))
                .build();
    }

    @Bean
    public ChatClient toolBacksChatClient(ChatClient.Builder builder,
                                          ObjectProvider<ToolCallbackProvider> toolCallbackProviders) {
        final var callbacks = toolCallbackProviders.stream()
                .map(ToolCallbackProvider::getToolCallbacks)
                .flatMap(Arrays::stream)
                .toArray(ToolCallback[]::new);
        return builder
                .defaultOptions(ChatOptions.builder().temperature(chatOptionsTemperature))
                .defaultTools((Object[]) callbacks)
                .build();
    }

    @Bean
    public ChatClient vectorStoreChatClient(ChatClient.Builder builder, VectorStore vectorStore) {
        return builder
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore)
                        .searchRequest(SearchRequest.builder()
                                .topK(topK)
                                .similarityThreshold(similarityThreshold)
                                .build())
                        .build())
                .build();
    }
}
