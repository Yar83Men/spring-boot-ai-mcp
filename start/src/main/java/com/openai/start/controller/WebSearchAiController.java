package com.openai.start.controller;

import com.openai.start.dto.web_search.WebSearchRequest;
import com.openai.start.dto.web_search.WebSearchResponse;
import com.openai.start.service.WebSearchAiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
public class WebSearchAiController {
    private final WebSearchAiService service;

    public WebSearchAiController(WebSearchAiService service) {
        this.service = service;
    }

    @PostMapping("/selenium-web-search")
    public WebSearchResponse seleniumWebSearch(@RequestBody WebSearchRequest request) {
        return service.seleniumSearch(request);
    }
}
