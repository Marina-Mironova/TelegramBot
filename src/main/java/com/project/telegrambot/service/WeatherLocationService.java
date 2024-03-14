package com.project.telegrambot.service;

import com.project.telegrambot.controller.HTTPResponseController;
import com.project.telegrambot.controller.JSONObjectMapperDeserialization;
import com.project.telegrambot.dto.WeatherMain;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONObject;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service

public class WeatherLocationService implements JSONObjectMapperDeserialization, HTTPResponseController {

    final String LOCATION_URL = new WeatherMain().getACCU_WEATHER_LOCATION_URL();
    final String API_KEY = new WeatherMain().getACCU_WEATHER_API_KEY();
    final String WEATHER_URL_NOW = new WeatherMain().getACCU_WEATHER_URL_NOW();

    public static Object cityAsk(){
        SendMessage message = new SendMessage();
        message.setText("Please, write the name of the city.");
        return null;
    }

    public void cityRequest(String cityName, String url) {//тут что-то не так!!!! Надо вчитаться
        try {
            HttpResponse<String> city = Unirest.get(LOCATION_URL)
                    .queryString("apiKey", API_KEY)
                    .queryString("q", cityName)
                    .asString();
        } catch (Exception e) {
            //TODO add exception to log
            cityAsk();

        }
        finally {
            try{
                locationKey(cityName);
            } catch (Exception e) {
                throw new RuntimeException(e);
                //TODO add to log
            }
        }
    }

       public String locationKey(String cityName) {
           String locationKey = Unirest.get(LOCATION_URL)
                   .asJson()
                   .getBody()
                   .getObject()
                   .getJSONObject("Key")
                   .toString(); //????
           //TODO вставить сеттер для ACCU_WEATHER_LOCATION_KEY ?
           return locationKey;
       }


    //получение текущей погоды
    private JSONObject getCurrentWeatherObject(String locationKey) throws Exception {
        HttpResponse<JsonNode> response = Unirest.get(WEATHER_URL_NOW)
                .routeParam("locationKey", locationKey)
                .queryString("apiKey", API_KEY)
                .asJson();
        return response.getBody().getObject();
    }

    /**
     * Get current weather
     * @param city City to get
     * @return Current weather
     * @throws Exception

    private CurrentWeather getCurrentWeather(String city) throws Exception {
        try {
            JSONObject weatherObject = getWeatherObject("weather", city);
            CurrentWeather weather = JsonUtil.toObject(weatherObject, CurrentWeather.class);
            if(weather == null) {
                throw new Exception("Cannot parse weather");
            }
            return weather;
        } catch(Exception e) {
            logger.error("Cannot get weather data", e);
            throw e;
        }
    }
     */

}
