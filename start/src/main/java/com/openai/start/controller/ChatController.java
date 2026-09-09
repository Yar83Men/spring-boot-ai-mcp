package com.openai.start.controller;

import com.openai.start.ChatAIService;
import com.openai.start.entity.ResponseEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/ai")
public class ChatController {
    private final ChatAIService service;

    public ChatController(ChatAIService service) {
        this.service = service;
    }

    @PostMapping("/message")
    public String answer(@RequestBody String question) {
        return service.answer(question);
    }

    @PostMapping("/prompt")
    public ResponseEntity prompt(@RequestBody String question) {
        return service.prompt(question);
    }

    @GetMapping("/chat-list")
    public List<String> chatList(@RequestParam(value = "director", defaultValue = "Джеймс Кэмерон") String director) {
        return service.chatList(director);
    }

    @GetMapping("/chat-map")
    public Map<String, Object> chatMap(@RequestParam(value = "director", defaultValue = "Джеймс Кэмерон") String director) {
        return service.chatMap(director);
    }

    @GetMapping("/exchange")
    public String exchange(@RequestParam(value = "message", defaultValue = "Курс валют на дату ") String message) {
        return service.exchange(message);
    }
}
