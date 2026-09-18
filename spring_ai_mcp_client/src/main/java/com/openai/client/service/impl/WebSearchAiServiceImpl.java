package com.openai.client.service.impl;

import com.openai.client.dto.web_search.WebSearchRequest;
import com.openai.client.dto.web_search.WebSearchResponse;
import com.openai.client.service.WebSearchAiService;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
public class WebSearchAiServiceImpl implements WebSearchAiService {
    private final ChatClient chatClient;
    @Value("classpath:/prompts/web-search-prompt.txt")
    private Resource webSearchPrompt;
    private final double chatOptionsTemperature;

    public WebSearchAiServiceImpl(ChatClient.Builder builder,
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
    public WebSearchResponse webSearch(@NotNull WebSearchRequest request) {
        final PromptTemplate template = new PromptTemplate(webSearchPrompt);
        final Prompt prompt = template.create(Map.of("url", request.url(),
                "action", request.action(),
                "limit", request.limit(),
                "sortBy", request.sortBy()));
        return chatClient.prompt(prompt)
                .call()
                .entity(WebSearchResponse.class);
    }
}
