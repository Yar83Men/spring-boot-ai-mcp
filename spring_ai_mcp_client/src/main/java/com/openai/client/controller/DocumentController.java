package com.openai.client.controller;

import com.openai.client.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.openai.client.constant.Constant.DEFAULT_URI;

@RestController
@RequestMapping(DEFAULT_URI)
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(value = "/fila/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Сохранение файла в RAG", description = "Загрузите файл для хранения в RAG")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Пустой файл");
        }
        documentService.parseAndSaveToRAG(file);
        return ResponseEntity.ok("Успешное сохранение файла " + file.getName());
    }
}
