package com.openai.start.controller;

import com.openai.start.dto.VectorStoreRequest;
import com.openai.start.service.ChatAIService;
import com.openai.start.service.RegStoreService;
import com.openai.start.service.impl.ChatAIServiceImpl;
import com.openai.start.entity.ResponseEntity;
import com.openai.start.service.impl.RegStoreServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.jspecify.annotations.NonNull;
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
    @Operation(summary = "Запрос на openai", description = "Отвечает CHAT-GPT Luna 5.6")
    public String answer(@RequestBody String question) {
        return chatAIService.answer(question);
    }

    @PostMapping("/prompt")
    @Operation(summary = "Запрос на openai", description = "Отвечает CHAT-GPT Luna 5.6")
    public ResponseEntity prompt(@RequestBody String question) {
        return chatAIService.prompt(question);
    }

    @GetMapping("/chat-list")
    @Operation(summary = "Запрос на openai", description = "Отвечает CHAT-GPT Luna 5.6, построение списка фильмов режиссера")
    public List<String> chatList(@RequestParam(value = "director", defaultValue = "Джеймс Кэмерон") String director) {
        return chatAIService.chatList(director);
    }

    @GetMapping("/chat-map")
    @Operation(summary = "Запрос на openai", description = "Отвечает CHAT-GPT Luna 5.6, " +
            "построение списка фильмов режиссера в виде ассоциативного массива")
    public Map<String, Object> chatMap(@RequestParam(value = "director", defaultValue = "Джеймс Кэмерон") String director) {
        return chatAIService.chatMap(director);
    }

    @GetMapping("/exchange")
    @Operation(summary = "Запрос на openai", description = "Отвечает CHAT-GPT Luna 5.6, курсы валют")
    public String exchange(@RequestParam(value = "message", defaultValue = "Курс валют на дату ") String message) {
        return chatAIService.exchange(message);
    }

    @PostMapping("/get-answer-from-vector-store")
    @Operation(summary = "Запрос на openai", description = "Отвечает CHAT-GPT Luna 5.6, " +
            "получение структурированной информации по данным с RAG хранилища, ранее загруженным данным")
    public String vectorStore(@NonNull @RequestBody VectorStoreRequest request) {
        return regStoreService.getFromVectorStore(request.question());
    }
}
