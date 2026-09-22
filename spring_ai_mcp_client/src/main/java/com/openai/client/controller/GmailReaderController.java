package com.openai.client.controller;

import com.openai.client.service.GmailSubjectsReaderService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.openai.client.constant.Constant.DEFAULT_URI;

@RestController
@RequestMapping(DEFAULT_URI)
public class GmailReaderController {
    private final GmailSubjectsReaderService service;

    public GmailReaderController(GmailSubjectsReaderService service) {
        this.service = service;
    }

    @PostMapping("/email-subject")
    public ResponseEntity<?> getGmailSubjects(@RequestBody @NonNull String message) {
        return ResponseEntity.ok(service.getGmailSubjects(message));
    }
}
