package com.openai.start.service.impl;

import com.openai.start.dto.web_search.WebSearchRequest;
import com.openai.start.dto.web_search.WebSearchResponse;
import com.openai.start.service.WebSearchAiService;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.ChatClient;
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
    private Resource seleniumSearchPrompt;

    public WebSearchAiServiceImpl(ChatClient.Builder builder, ObjectProvider<ToolCallbackProvider> toolCallbackProviders) {
        final var callbacks = toolCallbackProviders.stream()
                .map(ToolCallbackProvider::getToolCallbacks)
                .flatMap(Arrays::stream)
                .toArray(ToolCallback[]::new);

        this.chatClient = builder
                .defaultToolCallbacks(callbacks)
                .build();
    }

    @Override
    public WebSearchResponse webSearch(@NotNull WebSearchRequest request) {
        final PromptTemplate template = new PromptTemplate(seleniumSearchPrompt);
        final Prompt prompt = template.create(Map.of("url", request.url(),
                "action", request.action(),
                "offset", request.offset(),
                "sort", request.sort()));
        return chatClient.prompt(prompt)
                .call()
                .entity(WebSearchResponse.class);
    }
}
