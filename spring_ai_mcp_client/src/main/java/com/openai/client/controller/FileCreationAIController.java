package com.openai.client.controller;

import com.openai.client.dto.files.FileCreatingAIRequest;
import com.openai.client.service.FileCreatingAIService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.openai.client.constant.Constant.DEFAULT_URI;

@RestController
@RequestMapping(DEFAULT_URI)
public class FileCreationAIController {
    private final FileCreatingAIService service;

    public FileCreationAIController(FileCreatingAIService service) {
        this.service = service;
    }

    @PostMapping("/create-files")
    public ResponseEntity<?> fileCreate(@NonNull @RequestBody FileCreatingAIRequest request) {
        return ResponseEntity.ok(service.fileCreate(request));
    }
}
