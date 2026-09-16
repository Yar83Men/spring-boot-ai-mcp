package com.openai.start.dto.web_search;

public record WebSearchRequest(String url, String action, int offset, String sortBy) {
}
