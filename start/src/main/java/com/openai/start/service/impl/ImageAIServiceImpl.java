package com.openai.start.service.impl;

import com.openai.start.service.ImageAIService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageAIServiceImpl implements ImageAIService {
    private final Logger LOGGER = LoggerFactory.getLogger(ImageAIServiceImpl.class);
    private final ChatClient chatClient;

    public ImageAIServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public String getAIDescription(@NonNull MultipartFile file) {
        final InputStreamResource resource = uploadImage(file);
        final var media = new Media(MimeTypeUtils.IMAGE_JPEG, resource);
        final var userMessage = UserMessage.builder()
                .text("Опиши что ты видишь на изображении")
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
