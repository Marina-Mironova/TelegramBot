package com.project.telegrambot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.telegrambot.model.entities.User;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.StringResponse;
import kong.unirest.core.Unirest;

import java.util.List;

public interface HTTPResponseController {
    public default String httpResponseJson(String apiUrl) {
        String jsonString = null;
        try {
            // Выполняем GET-запрос к веб-сервису
            HttpResponse<String> jsonResponse = Unirest.get(apiUrl)
                    .asString();

            // Получаем JSON-тело ответа
            jsonString = jsonResponse.getBody();


        } catch (Exception e) {
            // Обрабатываем возможные исключения
            e.printStackTrace();
        }
        return jsonString;
    }
}
