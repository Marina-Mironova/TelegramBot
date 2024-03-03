package com.project.telegrambot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.telegrambot.dto.CurrentWeather;
import kong.unirest.core.JsonNode;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface JSONObjectMapperDeserialization {

    public default void JSONtoPOJO(JsonNode responseBody) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    CurrentWeather currentWeather = mapper.readValue((DataInput) responseBody, CurrentWeather.class);
    }


    public default void JSONtoList(JsonNode responseBody ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("unchecked")
        List<CurrentWeather> currentWeatherList = (List<CurrentWeather>) mapper.readValue((DataInput) responseBody, CurrentWeather.class);

    }

/*
    public default void JSONtoMap(JsonNode responseBody) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> currentWeatherMap = mapper.readValue((DataInput) responseBody, CurrentWeather.class);

    }
*/




}
