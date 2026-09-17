package com.openai.client.service;

import com.openai.client.dto.web_search.WebSearchRequest;
import com.openai.client.dto.web_search.WebSearchResponse;
import org.jspecify.annotations.NonNull;

public interface WebSearchAiService {
    WebSearchResponse webSearch(@NonNull WebSearchRequest request);
}
