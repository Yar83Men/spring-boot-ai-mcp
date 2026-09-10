package com.openai.start.controller;

import com.openai.start.service.ChatAIService;
import com.openai.start.service.RegStoreService;
import com.openai.start.service.impl.ChatAIServiceImpl;
import com.openai.start.entity.ResponseEntity;
import com.openai.start.service.impl.RegStoreServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
public class ChatController {
    private final ChatAIService chatAIService;
    private final RegStoreService regStoreService;

    public ChatController(ChatAIServiceImpl chatAIService, RegStoreServiceImpl regStoreService) {
        this.chatAIService = chatAIService;
        this.regStoreService = regStoreService;
    }

    @PostMapping("/message")
    public String answer(@RequestBody String question) {
        return chatAIService.answer(question);
    }

    @PostMapping("/prompt")
    public ResponseEntity prompt(@RequestBody String question) {
        return chatAIService.prompt(question);
    }

    @GetMapping("/chat-list")
    public List<String> chatList(@RequestParam(value = "director", defaultValue = "Джеймс Кэмерон") String director) {
        return chatAIService.chatList(director);
    }

    @GetMapping("/chat-map")
    public Map<String, Object> chatMap(@RequestParam(value = "director", defaultValue = "Джеймс Кэмерон") String director) {
        return chatAIService.chatMap(director);
    }

    @GetMapping("/exchange")
    public String exchange(@RequestParam(value = "message", defaultValue = "Курс валют на дату ") String message) {
        return chatAIService.exchange(message);
    }
    @PostMapping("/vector-store")
    public String vectorStore(@RequestBody String question) {
        return regStoreService.getFromVectorStore(question);
    }
}
