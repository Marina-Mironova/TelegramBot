package com.project.telegrambot.service;

import com.project.telegrambot.controller.HTTPResponseController;
import com.project.telegrambot.controller.JsonUtil;
import com.project.telegrambot.dto.CurrentWeather;
import com.project.telegrambot.dto.WeatherMain;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONObject;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;

import static com.sun.org.apache.xml.internal.serializer.utils.Utils.messages;
//import org.json.JSONObject;

@Service

public class WeatherLocationService{

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

     * @return Current weather
     * @throws Exception
     * */

    private CurrentWeather getCurrentWeather() throws Exception {
        try {
            JSONObject weatherObject = getCurrentWeatherObject(locationKey(cityName));
            //TODO написать метод дя чтения названия города на английском
            CurrentWeather weather = JsonUtil.toObject(weatherObject, CurrentWeather.class);
            if(weather == null) {
                throw new Exception("Cannot parse weather");
            }
            return weather;
        } catch(Exception e) {
            //logger.error("Cannot get weather data", e);
            throw e;
        }
    }



    /**
     * Send weather in city to chat
     * @param chat Chat

     */
    private void sendCurrentWeather(Chat chat, String locationKey) {
        try {
            CurrentWeather currentWeather = getCurrentWeather();

            int temperature = (int)Math.round(currentWeather.getTemperatureCurrent().getTempMetricCurrent().getValue());
            String unit = String.format(currentWeather.getTemperatureCurrent().getTempMetricCurrent().getUnit());
            String localDateTime = String.format(currentWeather.getLocalDateTime());
            boolean isDayTime;

            String link = String.format(currentWeather.getLink());

            String weatherText = String.format(messages.getString("current_weather_format"),
                    localDateTime,
                    currentWeather.getWeatherText(),
                    temperature, unit, link);


            sendReply(chat, weather);
        } catch(Exception e) {
            logger.error("Weather error", e);
            sendReply(chat, messages.getString("weather_get_error"));
        }
    }

}


