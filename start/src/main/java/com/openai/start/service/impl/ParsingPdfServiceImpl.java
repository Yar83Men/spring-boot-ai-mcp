package com.openai.start.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ParsingPdfServiceImpl implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(ParsingPdfServiceImpl.class);
    private final VectorStore vectorStore;
    @Value("classpath:/docs/spring_boot4.pdf")
    private Resource pdfFile;

    public ParsingPdfServiceImpl(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {
        final var pdfReader = new TikaDocumentReader(pdfFile);
        final var textSplitter = new TokenTextSplitter();
        vectorStore.accept(textSplitter.apply(pdfReader.get()));
        LOG.info("Данные PDF в vectorStore");
    }
}
