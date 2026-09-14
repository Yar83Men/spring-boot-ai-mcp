package com.openai.start.service;

import com.openai.start.entity.ResponseEntity;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Map;

public interface ChatAIService {
    String answer(@NonNull String question);

    ResponseEntity prompt(@NonNull String question);

    List<String> chatList(@NonNull String director);

    Map<String, Object> chatMap(@NonNull String director);
}
