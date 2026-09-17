package com.openai.client.dto.error;

public record ErrorMessageResponse(String uri, String errorMessage, String errorStatus) {
}
