package com.openai.client.controller;

import com.openai.client.dto.chat.InMemoryChatRequest;
import com.openai.client.dto.vector_store.VectorStoreRequest;
import com.openai.client.service.ChatAIService;
import com.openai.client.service.ChatMemoryService;
import com.openai.client.service.RegStoreService;
import com.openai.client.service.impl.ChatAIServiceImpl;
import com.openai.client.service.impl.RegStoreServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.openai.client.constant.Constant.*;


@RestController
@RequestMapping(DEFAULT_URI)
public class ChatController {
    private final ChatAIService chatAIService;
    private final RegStoreService regStoreService;
    private final ChatMemoryService chatMemoryService;

    public ChatController(ChatAIServiceImpl chatAIService,
                          RegStoreServiceImpl regStoreService,
                          ChatMemoryService chatMemoryService) {
        this.chatAIService = chatAIService;
        this.regStoreService = regStoreService;
        this.chatMemoryService = chatMemoryService;
    }

    @PostMapping("/message")
    @Operation(summary = REQUEST_TO_OPEN_API_TEXT, description = ANSWER_FROM_OPEN_API_TEXT)
    public ResponseEntity<?> answer(@Parameter(description = ANSWER_FROM_OPEN_API_TEXT) @RequestBody String question) {
        return ResponseEntity.ok(chatAIService.answer(question));
    }

    @PostMapping("/get-answer-from-vector-store")
    @Operation(summary = REQUEST_TO_OPEN_API_TEXT, description = ANSWER_FROM_OPEN_API_TEXT + CONTROLLER_DESCRIPTION_RAG)
    public ResponseEntity<?> vectorStore(@Parameter(description = "Запрос на отработку данных с vector-store, " +
            "предварительно загрузив в хранилище данные") @NonNull @RequestBody VectorStoreRequest request) {
        return ResponseEntity.ok(regStoreService.getFromVectorStore(request.question()));
    }

    @PostMapping("/chat-with-memory")
    @Operation(summary = CONTROLLER_REDIS_TEXT, description = ANSWER_FROM_OPEN_API_TEXT)
    public ResponseEntity<?> chatWithMemory(@Parameter(description = "Обработка данных сохраненных в redis",
            example = EXAMPLE_REQUEST_FOR_MEMORY_CHAT)
                                            @NonNull @RequestBody InMemoryChatRequest request) {
        return ResponseEntity.ok(chatMemoryService.chatWithMemory(request.message(), request.conversationId()));
    }

    @PostMapping("/chat-details")
    @Operation(summary = "Детализация запроса, мета-данные запроса")
    private ResponseEntity<?> chatResponse(@NonNull @RequestBody String message) {
        return ResponseEntity.ok(chatAIService.getAIDetails(message));
    }
}
