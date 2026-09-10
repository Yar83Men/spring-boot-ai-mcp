package com.openai.start.service;

import org.jspecify.annotations.NonNull;

public interface RegStoreService {
    String getFromVectorStore(@NonNull String question);
}
