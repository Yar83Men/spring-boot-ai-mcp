package com.openai.client.service;

import com.openai.client.dto.vector_store.VectorStoreAIResponse;
import org.jspecify.annotations.NonNull;

public interface RegStoreService {
    VectorStoreAIResponse getFromVectorStore(@NonNull String question);
}
