package com.openai.start.controller;

import com.openai.start.dto.chat.InMemoryChatRequest;
import com.openai.start.dto.vector_store.VectorStoreAIResponse;
import com.openai.start.dto.vector_store.VectorStoreRequest;
import com.openai.start.entity.InMemoryChatEntity;
import com.openai.start.service.ChatAIService;
import com.openai.start.service.ChatMemoryService;
import com.openai.start.service.RegStoreService;
import com.openai.start.service.impl.ChatAIServiceImpl;
import com.openai.start.service.impl.RegStoreServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

import static com.openai.start.Constant.DEFAULT_URI;


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
    @Operation(summary = "Запрос на openai", description = "Отвечает CHAT-GPT Luna 5.6")
    public String answer(@Parameter(description = "Вопрос для получения ответа AI GPT") @RequestBody String question) {
        return chatAIService.answer(question);
    }

    @PostMapping("/get-answer-from-vector-store")
    @Operation(summary = "Запрос на openai", description = "Отвечает CHAT-GPT Luna 5.6, " +
            "получение структурированной информации по данным с RAG хранилища, ранее загруженным данным")
    public VectorStoreAIResponse vectorStore(@Parameter(description = "Запрос на отработку данных с vector-store, " +
            "предварительно загрузив в хранилище данные") @NonNull @RequestBody VectorStoreRequest request) {
        return regStoreService.getFromVectorStore(request.question());
    }

    @PostMapping("/chat-with-memory")
    @Operation(summary = "Сохранении диалога по conversationId в Redis", description = "Отвечает CHAT-GPT Luna 5.6")
    public InMemoryChatEntity chatWithMemory(@Parameter(description = "Обработка данных сохраненных в redis",
            example = "Первый запрос - меня зовут Иван Иванов 33 года,  потом в произвольной форме задаете вопрос, обязательно укажите conversationId")
                                             @NonNull @RequestBody InMemoryChatRequest request) {
        return chatMemoryService.chatWithMemory(request.message(), request.conversationId());
    }

    @PostMapping("/chat-details")
    @Operation(summary = "Детализация запроса, мета-данные запроса")
    private ChatResponse chatResponse(@NonNull @RequestBody String message) {
        return chatAIService.getAIDetails(message);
    }
}
