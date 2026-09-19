package com.openai.client.constant;

public interface Constant {
    String DEFAULT_URI = "/api/v1/ai";
    String REQUEST_TO_OPEN_API_TEXT = "Запрос на openai";
    String ANSWER_FROM_OPEN_API_TEXT = "Отвечает CHAT-GPT 5.6";
    String CONTROLLER_DESCRIPTION_RAG = "получение структурированной информации по данным с RAG хранилища, ранее загруженным данным";
    String CONTROLLER_REDIS_TEXT = "Сохранении диалога по conversationId в Redis";
    String EXAMPLE_REQUEST_FOR_MEMORY_CHAT = "Первый запрос - меня зовут Иван Иванов 33 года,  потом в произвольной форме задаете вопрос, обязательно укажите conversationId";
    String CHOOSE_FILE_FOR_UPLOAD = "Выбор JPEG файла на распознавание";
    String FILES_UPLOADS_LIST = "Загрузите файл только формат JPEG, PNG, WEBP, GIF";
}
