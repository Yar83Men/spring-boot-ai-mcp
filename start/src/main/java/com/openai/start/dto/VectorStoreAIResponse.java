package com.openai.start.dto;

import java.util.List;

public record VectorStoreAIResponse(String inputMessage, String shortAnswer, List<String> completeAnswer) {
}
