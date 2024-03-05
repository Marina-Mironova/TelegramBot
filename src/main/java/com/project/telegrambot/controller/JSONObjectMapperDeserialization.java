package com.project.telegrambot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public interface JSONObjectMapperDeserialization {

    public default void JSONtoPOJO(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object object = mapper.readValue(json, new TypeReference<Object>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public default void JSONtoList(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Object> objectList = mapper.readValue(json, new TypeReference<List<Object>>() {});

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
