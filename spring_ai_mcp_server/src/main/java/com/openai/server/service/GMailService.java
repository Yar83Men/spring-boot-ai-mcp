package com.openai.server.service;

import jakarta.mail.*;
import jakarta.mail.search.FlagTerm;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.Properties;

@Service
public class GMailService {
    private final String mailHost;
    private final int emailPort;
    private final String emailUser;
    private final String emailAppPassword;

    public GMailService(@Value("${gmail.host}") String mailHost,
                        @Value("${gmail.port}") int emailPort,
                        @Value("${gmail.user}") String emailUser,
                        @Value("${gmail.password}") String emailAppPassword) {
        this.mailHost = mailHost;
        this.emailPort = emailPort;
        this.emailUser = emailUser;
        this.emailAppPassword = emailAppPassword;
    }

    @Tool(description = "Получение тем писем с почты @gmail.com")
    public List<String> fetchEmail() {
        try {
            final var session = Session.getDefaultInstance(buildProperties());
            final var store = session.getStore();
            store.connect(mailHost, emailUser, emailAppPassword);
            final var inboxFolder = store.getFolder("INBOX");
            inboxFolder.open(Folder.READ_ONLY);
            final var flags = new Flags(Flags.Flag.RECENT);
            Message[] messages = inboxFolder.search(new FlagTerm(flags, false));

            final var result = new ArrayList<String>(messages.length);
            for (final var message : messages) {
                result.add(message.getSubject());
            }

            inboxFolder.close();
            store.close();
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка при получении писем из Gmail", ex);
        }
    }

    private Properties buildProperties() {
        final var properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", mailHost);
        properties.put("mail.imaps.port", emailPort);
        properties.put("mail.imaps.ssl.enable", "true");
        return properties;
    }
}
