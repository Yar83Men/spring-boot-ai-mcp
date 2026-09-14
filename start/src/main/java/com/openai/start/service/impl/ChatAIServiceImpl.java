package com.openai.start.service.impl;

import com.openai.start.entity.ResponseEntity;
import com.openai.start.service.ChatAIService;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ChatAIServiceImpl implements ChatAIService {
    private final ChatClient chatClient;
    @Value("classpath:/prompts/exchange.st")
    private Resource exhangeResource;
    @Value("classpath:/docs/exchanges-variant.txt")
    private Resource variant;


    final SystemMessage systemMessage = new SystemMessage("Ответ должен быть кратким, не детализированным");
    final MapOutputConverter mapOutputConverter = new MapOutputConverter();


    public ChatAIServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public String answer(@NonNull String question) {
        return this.chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    @Override
    public ResponseEntity prompt(@NonNull String question) {
        final var userMessage = new UserMessage(question);
        final var prompt = new Prompt(userMessage, systemMessage);
        return chatClient
                .prompt(prompt)
                .call()
                .entity(ResponseEntity.class);
    }

    @Override
    public List<String> chatList(@NonNull String director) {
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

    @Override
    public Map<String, Object> chatMap(@NonNull String director) {
        final var message = """
                    Список фильмов режисcера {director} и даты выхода фильмов
                    {format}
                """;

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
