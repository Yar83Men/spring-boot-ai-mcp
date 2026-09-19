package com.openai.client.service.impl;

import com.openai.client.dto.files.FileCreatingAIRequest;
import com.openai.client.dto.files.FilesCreatingAIResponse;
import com.openai.client.service.FileCreatingAIService;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FileCreatingAIServiceImpl implements FileCreatingAIService {
    private final ChatClient chatClient;
    @Value("classpath:/prompts/file-creating-prompt.txt")
    private Resource promptTemplate;


    public FileCreatingAIServiceImpl(@Qualifier("toolBacksChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public FilesCreatingAIResponse fileCreate(@NotNull FileCreatingAIRequest request) {
        final PromptTemplate template = new PromptTemplate(promptTemplate);
        final Prompt prompt = template.create(Map.of("action", request.action()));
        return chatClient.prompt(prompt)
                .call()
                .entity(FilesCreatingAIResponse.class);
    }
}
