package com.openai.client.service;

import com.openai.client.dto.files.FileCreatingAIRequest;
import com.openai.client.dto.files.FilesCreatingAIResponse;
import org.jetbrains.annotations.NotNull;

public interface FileCreatingAIService {
    FilesCreatingAIResponse fileCreate(@NotNull FileCreatingAIRequest request);
}
