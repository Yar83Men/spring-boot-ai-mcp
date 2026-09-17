package com.openai.client.service;

import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.model.ChatResponse;


public interface ChatAIService {
    String answer(@NonNull String question);

    ChatResponse getAIDetails(@NonNull String message);
}
