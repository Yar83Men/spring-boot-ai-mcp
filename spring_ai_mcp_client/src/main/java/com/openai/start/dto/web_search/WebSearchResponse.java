package com.openai.start.dto.web_search;

import java.util.List;

public record WebSearchResponse(String url, List<String> result) {
}
