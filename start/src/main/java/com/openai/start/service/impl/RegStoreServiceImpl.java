package com.openai.start.service.impl;

import com.openai.start.service.RegStoreService;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class RegStoreServiceImpl implements RegStoreService {
    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final int TOP_K = 5;
    private final double SIMILARITY_THRESHOLD = 0.7;

    public RegStoreServiceImpl(ChatClient.Builder builder, VectorStore vectorStore) {
        this.vectorStore = vectorStore;
        this.chatClient = builder.defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).searchRequest(SearchRequest.builder().topK(TOP_K).similarityThreshold(SIMILARITY_THRESHOLD).build()).build()).build();
    }

    @Override
    public String getFromVectorStore(@NonNull String question) {
        return chatClient.prompt().user(question).call().content();
    }
}
