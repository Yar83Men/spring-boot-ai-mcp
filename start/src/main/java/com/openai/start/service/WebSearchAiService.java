package com.openai.start.service;

import com.openai.start.dto.web_search.WebSearchRequest;
import com.openai.start.dto.web_search.WebSearchResponse;
import org.jspecify.annotations.NonNull;

public interface WebSearchAiService {
    WebSearchResponse webSearch(@NonNull WebSearchRequest request);
}
