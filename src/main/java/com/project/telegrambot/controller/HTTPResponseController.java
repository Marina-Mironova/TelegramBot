package com.project.telegrambot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.telegrambot.model.entities.User;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;

import java.util.List;

public interface HTTPResponseController {
    public default JsonNode httpResponseJson(String apiUrl){
        JsonNode responseBody = null;
    try {
        // Выполняем GET-запрос к веб-сервису
        HttpResponse<JsonNode> jsonResponse = Unirest.get(apiUrl)
                .header("Accept", "application/json")
                .asJson();

        // Получаем JSON-тело ответа
        responseBody = jsonResponse.getBody();


    } catch (Exception e) {
        // Обрабатываем возможные исключения
        e.printStackTrace();
    }
        return responseBody;
    }
}
