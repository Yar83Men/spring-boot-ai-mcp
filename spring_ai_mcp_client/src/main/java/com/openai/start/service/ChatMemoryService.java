package com.openai.start.service;

import com.openai.start.entity.InMemoryChatEntity;
import org.jspecify.annotations.NonNull;

public interface ChatMemoryService {
    InMemoryChatEntity chatWithMemory(@NonNull String message, @NonNull String conversationId);
}
