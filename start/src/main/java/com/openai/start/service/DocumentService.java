package com.openai.start.service;

import org.jspecify.annotations.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    void parseAndSaveToRAG(@NonNull MultipartFile file);
}
