package com.openai.start.service;

import com.openai.start.dto.VectorStoreAIResponse;
import org.jspecify.annotations.NonNull;

public interface RegStoreService {
    VectorStoreAIResponse getFromVectorStore(@NonNull String question);
}
