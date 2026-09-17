package com.openai.client.service;

import com.openai.client.entity.InMemoryChatEntity;
import org.jspecify.annotations.NonNull;

public interface ChatMemoryService {
    InMemoryChatEntity chatWithMemory(@NonNull String message, @NonNull String conversationId);
}
