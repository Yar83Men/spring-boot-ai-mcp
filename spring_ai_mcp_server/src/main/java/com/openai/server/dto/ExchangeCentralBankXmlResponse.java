package com.openai.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.openai.server.util.CommaBigDecimalDeserializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JacksonXmlRootElement(localName = "ValCurs")
public record ExchangeCentralBankXmlResponse(
        @JacksonXmlProperty(isAttribute = true, localName = "Date")
        @JsonPropertyDescription("Курс валют ЦБ РФ на дату")
        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate date,

        @JacksonXmlProperty(isAttribute = true, localName = "name")
        String name,

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "Valute")
        @JsonPropertyDescription("Список валют")
        List<Valute> valutes) {

    public record Valute(
            @JacksonXmlProperty(isAttribute = true, localName = "ID")
            String id,
            @JsonPropertyDescription("Цифровой код валюты")
            @JacksonXmlProperty(localName = "NumCode")
            String numCode,
            @JsonPropertyDescription("Символьный код валюты")
            @JacksonXmlProperty(localName = "CharCode")
            String charCode,
            @JsonPropertyDescription("Номинал")
            @JacksonXmlProperty(localName = "Nominal")   int nominal,
            @JacksonXmlProperty(localName = "Name")      String name,
            @JsonPropertyDescription("Количество рублей РФ (RUB) за 1 номинал")
            @JacksonXmlProperty(localName = "Value")
            @JsonDeserialize(using = CommaBigDecimalDeserializer.class)
            BigDecimal value,

            @JacksonXmlProperty(localName = "VunitRate")
            @JsonDeserialize(using = CommaBigDecimalDeserializer.class)
            BigDecimal vunitRate) {}
}
