package com.openai.server.service;

import com.openai.server.dto.ExchangeCentralBankXmlResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ExchangeCbrToolService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeCbrToolService.class);
    private final RestClient restClient;
    private final String EXCHANGE_API_URL = "https://cbr.ru/";
    private final String DATE_FORMAT = "dd/MM/yyyy";
    private final String API_URI = "/scripts/XML_daily.asp";
    private final String QUERY_PARAM = "date_req";

    public ExchangeCbrToolService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl(EXCHANGE_API_URL)
                .build();
    }

    @Tool(description = "Получение курса валют ЦБ РФ на указанную в запросе дату")
    public ExchangeCentralBankXmlResponse getCbrExchangeOnDate(
            @ToolParam(description = "Дата актуального курса валют") @NotNull LocalDate date) {
        final var dateFormate = date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        LOGGER.info("Запрос курсов ЦБ на {}", dateFormate);

        return restClient.get()
                .uri(uri ->
                        uri.path(API_URI)
                                .queryParam(QUERY_PARAM, dateFormate)
                                .build())
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .body(ExchangeCentralBankXmlResponse.class);
    }
}
