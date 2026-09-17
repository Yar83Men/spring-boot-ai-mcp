package com.openai.client.controller;

import com.openai.client.dto.web_search.WebSearchRequest;
import com.openai.client.dto.web_search.WebSearchResponse;
import com.openai.client.service.WebSearchAiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.openai.client.constant.Constant.DEFAULT_URI;

@RestController
@RequestMapping(DEFAULT_URI)
public class WebSearchAiController {
    private final WebSearchAiService service;

    public WebSearchAiController(WebSearchAiService service) {
        this.service = service;
    }

    @PostMapping("/web-search")
    public WebSearchResponse seleniumWebSearch(@RequestBody WebSearchRequest request) {
        return service.webSearch(request);
    }
}
