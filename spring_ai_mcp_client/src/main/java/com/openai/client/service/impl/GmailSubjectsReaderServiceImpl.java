package com.openai.client.service.impl;

import com.openai.client.service.GmailSubjectsReaderService;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class GmailSubjectsReaderServiceImpl implements GmailSubjectsReaderService {
    private final ChatClient chatClient;

    public GmailSubjectsReaderServiceImpl(@Qualifier("toolBacksChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String getGmailSubjects(@NonNull String message) {
        final var systemMessage = new SystemMessage("Ты умеешь работать с gmail.com почтой, воспользуйся 'Получение тем писем с почты @gmail.com'");
        return chatClient.prompt(new Prompt(systemMessage, new UserMessage(message)))
                .call()
                .content();
    }
}
