package com.openai.client.controller;

import com.openai.client.service.ImageAIService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.openai.client.constant.Constant.*;

@RestController
@RequestMapping(DEFAULT_URI)
public class ImageController {
    private final ImageAIService imageAIService;

    public ImageController(ImageAIService imageAIService) {
        this.imageAIService = imageAIService;
    }

    @PostMapping(value = "/image/upload-recognize", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = CHOOSE_FILE_FOR_UPLOAD, description = FILES_UPLOADS_LIST)
    public ResponseEntity<?> uploadImage(@RequestPart("question") String question, @RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return org.springframework.http.ResponseEntity.badRequest().body("Пустой файл");
        }

        return ResponseEntity.ok(imageAIService.getAIDescription(file, question));
    }
}
