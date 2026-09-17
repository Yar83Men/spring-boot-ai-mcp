package com.openai.server.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class CommaBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        final var text = p.getText();
        if (text == null || text.isBlank()) {
            return null;
        }
        return new BigDecimal(text.trim().replace(',', '.'));
    }
}
