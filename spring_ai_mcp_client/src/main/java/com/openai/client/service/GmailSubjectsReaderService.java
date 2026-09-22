package com.openai.client.service;

import org.jspecify.annotations.NonNull;

public interface GmailSubjectsReaderService {
    String getGmailSubjects(@NonNull String message);
}
