package com.project.telegrambot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kong.unirest.core.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Convert Json object to class instance
     * @param object Json object to convert
     * @param cls Class
     * @param <T> Some class
     * @return T
     */
    public static <T> T toObject(JSONObject object, Class<T> cls) {
        try {
            return (T)objectMapper.readValue(object.toString(), cls);
        } catch(ClassCastException e) {
            logger.error("Cannot cast to " + cls.getName(), e);
        } catch(Exception e) {
            logger.error("Unable to convert json to object", e);
        }
        return null;
    }

}

/*public interface JSONObjectMapperDeserialization {

    default void JSONtoPOJO(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object object = mapper.readValue(json, new TypeReference<>() {
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
            List<Object> objectList = mapper.readValue(json, new TypeReference<>() {
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}*/
