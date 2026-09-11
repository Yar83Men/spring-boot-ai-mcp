package com.openai.start.service;

import org.jspecify.annotations.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface ParsingPdfService {
    void parse(@NonNull MultipartFile file);
}
