package com.openai.start.dto.vector_store;

import java.util.List;

public record VectorStoreAIResponse(String inputMessage, String shortAnswer, List<String> completeAnswer) {
}
