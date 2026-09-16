package com.openai.start.configuration;


import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.redis.RedisChatMemoryRepository;
import org.springframework.boot.data.redis.autoconfigure.DataRedisConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.RedisClient;

import java.time.Duration;


@Configuration
public class ChatMemoryConfiguration {

    @Bean
    public ChatMemory chatMemory(RedisChatMemoryRepository redisChatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(redisChatMemoryRepository)
                .maxMessages(30)
                .build();
    }

    @Bean
    RedisChatMemoryRepository redisChatMemoryRepository(DataRedisConnectionDetails connectionDetails) {
        final var standalone = connectionDetails.getStandalone();
        final var jedisClient = RedisClient.builder()
                .hostAndPort(standalone.getHost(), standalone.getPort())
                .build();

        return RedisChatMemoryRepository.builder()
                .jedisClient(jedisClient)
                .timeToLive(Duration.ofMinutes(100))
                .build();

    }
}
