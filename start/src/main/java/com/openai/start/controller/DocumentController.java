package com.openai.start.controller;

import com.openai.start.service.ParsingPdfService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/doc")
public class DocumentController {
    private final ParsingPdfService parsingPdfService;

    public DocumentController(ParsingPdfService parsingPdfService) {
        this.parsingPdfService = parsingPdfService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Сохранение файла в RAG", description = "Загрузите файл для хранения в RAG")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Пустой файл");
        }
        parsingPdfService.parse(file);
        return ResponseEntity.ok("Успешное сохранение файла " + file.getName());
    }
}
