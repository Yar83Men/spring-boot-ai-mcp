package com.openai.start.controller;

import com.openai.start.service.ImageAIService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    private final ImageAIService imageAIService;

    public ImageController(ImageAIService imageAIService) {
        this.imageAIService = imageAIService;
    }

    @PostMapping(value = "/upload-recognize", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Выбор JPEG файла на распознавание", description = "Загрузите файл только формат JPEG")
    public ResponseEntity<?> uploadImage(@RequestPart("question") String question, @RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return org.springframework.http.ResponseEntity.badRequest().body("Пустой файл");
        }

        return ResponseEntity.ok(imageAIService.getAIDescription(file, question));
    }
}
