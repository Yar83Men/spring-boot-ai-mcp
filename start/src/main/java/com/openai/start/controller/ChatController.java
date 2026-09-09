package com.openai.start.controller;

import com.openai.start.entity.ResponseEntity;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class ChatController {
    private final ChatClient chatClient;
    final SystemMessage systemMessage = new SystemMessage("Ответ должен быть кратким, не детализированным");

    public ChatController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @PostMapping("/message")
    public String answer(@RequestBody String question) {
        return this.chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    @PostMapping("/prompt")
    public ResponseEntity prompt(@RequestBody String question) {
        final var userMessage = new UserMessage(question);
        final var prompt = new Prompt(userMessage, systemMessage);
        return chatClient
                .prompt(prompt)
                .call()
                .entity(ResponseEntity.class);
    }

    @GetMapping("/chat-list")
    public List<String> chatList(@RequestParam(value = "director", defaultValue = "Джеймс Кэмерон") String director) {
        final var message = """
                    Список фильмов режисcера {director}
                """;

        final PromptTemplate template = new PromptTemplate(message);
        final Prompt prompt = template.create(Map.of("director", director));
        final Message userMessage = prompt.getUserMessage();
        final Prompt finalPrompt = new Prompt(List.of(userMessage, systemMessage));
        final ListOutputConverter listOutputConverter = new ListOutputConverter(new DefaultConversionService());

        final ChatClient.CallResponseSpec respone = chatClient
                .prompt(finalPrompt)
                .call();
        return listOutputConverter.convert(Objects.requireNonNull(respone.content()));
    }

    @GetMapping("/chat-map")
    public Map<String, Object> chatMap(@RequestParam(value = "director", defaultValue = "Джеймс Кэмерон") String director) {
        final var message = """
                    Список фильмов режисcера {director} и даты выхода фильмов
                    {format}
                """;
        final MapOutputConverter mapOutputConverter = new MapOutputConverter();
        final PromptTemplate template = new PromptTemplate(message);
        final Prompt prompt = template.create(Map.of(
                "director", director,
                "format", mapOutputConverter.getFormat()));
        final Message userMessage = prompt.getUserMessage();
        final Prompt finalPrompt = new Prompt(List.of(userMessage, systemMessage));


        final var response = chatClient
                .prompt(finalPrompt)
                .call();
        return mapOutputConverter.convert(Objects.requireNonNull(response.content()));
    }
}
