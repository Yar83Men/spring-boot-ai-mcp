package com.openai.client.dto.web_search;

public record WebSearchRequest(String url, String action, Integer limit, String sortBy) {
}
