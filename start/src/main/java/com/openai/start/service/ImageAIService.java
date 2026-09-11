package com.openai.start.service;

import org.jspecify.annotations.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface ImageAIService {
    String getAIDescription(@NonNull MultipartFile file, String question);
}
