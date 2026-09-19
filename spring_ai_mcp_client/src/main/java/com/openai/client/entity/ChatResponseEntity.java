package com.openai.client.entity;

import java.time.LocalDate;

public record ChatResponseEntity(LocalDate date, String content, Long size) {
}
