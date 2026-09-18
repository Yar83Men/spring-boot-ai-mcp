package com.openai.client.service.impl;

import com.openai.client.service.ImageAIService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class ImageAIServiceImpl implements ImageAIService {
    private final Logger LOGGER = LoggerFactory.getLogger(ImageAIServiceImpl.class);
    private final ChatClient chatClient;
    private final double chatOptionsTemperature;
    private final String DEFAULT_QUESTION = "Опиши что ты видишь на изображении";

    public ImageAIServiceImpl(ChatClient.Builder builder,
                              @Value("${model.chat.options.temperature}") double chatOptionsTemperature) {
        this.chatClient = builder
                .defaultOptions(ChatOptions.builder().temperature(chatOptionsTemperature))
                .build();
        this.chatOptionsTemperature = chatOptionsTemperature;
    }

    @Override
    public String getAIDescription(@NonNull MultipartFile file, String question) {
        final var resource = uploadImage(file);
        final var media = new Media(MimeTypeUtils.IMAGE_JPEG, resource);
        final var userMessage = UserMessage.builder()
                .text(Objects.isNull(question) ? DEFAULT_QUESTION : question)
                .media(media)
                .build();

        final var prompt = new Prompt(userMessage);
        return chatClient.prompt(prompt)
                .call()
                .content();
    }

    private InputStreamResource uploadImage(@NonNull MultipartFile file) {
        try {
            return new InputStreamResource(file.getInputStream());
        } catch (IOException ex) {
            LOGGER.error("Ошибка загрузки изображения {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
