package com.openai.client.service.impl;

import com.openai.client.service.DocumentService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentServiceImpl implements DocumentService {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentServiceImpl.class);
    private final VectorStore vectorStore;

    public DocumentServiceImpl(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void parseAndSaveToRAG(@NonNull MultipartFile file) {
        try {
            final var resource = new InputStreamResource(file.getInputStream());
            final var pdfReader = new TikaDocumentReader(resource);
            final var textSplitter = new TokenTextSplitter();
            vectorStore.accept(textSplitter.apply(pdfReader.get()));
            LOG.info("Данные PDF успешно сохранены в vectorStore");
        } catch (Exception ex) {
            LOG.error("Ошибка сохранения PDF {}", ex.getMessage());
        }
    }
}
