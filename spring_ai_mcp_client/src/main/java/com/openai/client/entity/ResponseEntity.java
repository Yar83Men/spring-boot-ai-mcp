package com.openai.client.entity;

import java.time.LocalDate;

public record ResponseEntity(LocalDate date, String content, Long size) {
}
