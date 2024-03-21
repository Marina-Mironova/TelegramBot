package com.project.telegrambot.service;

import com.project.telegrambot.config.BotConfig;
import com.project.telegrambot.controller.JsonUtil;
import com.project.telegrambot.dto.CurrentWeather;
import com.project.telegrambot.dto.DailyForecasts;
import com.project.telegrambot.dto.WeatherForecastOneDay;
import com.project.telegrambot.dto.WeatherMain;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONObject;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


//import org.json.JSONObject;

@Service

public class WeatherService {

    final String LOCATION_URL = new WeatherMain().getACCU_WEATHER_LOCATION_URL();
    final String API_KEY = new WeatherMain().getACCU_WEATHER_API_KEY();
    final String WEATHER_URL_NOW = new WeatherMain().getACCU_WEATHER_URL_NOW();

    final String WEATHER_URL_DAILY = new WeatherMain().getACCU_WEATHER_URL_DAILY();

 /*   public WeatherService(BotConfig config) {
        super(config);
    }
*/

    public static Object cityAsk(){
        SendMessage message = new SendMessage();
        message.setText("Please, write the name of the city.");
        return null;
    }

    public void cityRequest(String cityName) {//тут что-то не так!!!! Надо вчитаться
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
                gettinglocationKey(cityName);
            } catch (Exception e) {
                throw new RuntimeException(e);
                //TODO add to log
            }
        }
    }

       public String gettinglocationKey(String cityName) {
           String locationKey = Unirest.get(LOCATION_URL)
                   .asJson()
                   .getBody()
                   .getObject()
                   .getJSONObject("Key")
                   .toString(); //????

           return locationKey; //TODO вставить сеттер для LOCATION_KEY ?
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

    private CurrentWeather getCurrentWeather(String cityName) throws Exception {
        try {
            JSONObject weatherObject = getCurrentWeatherObject(gettinglocationKey(cityName));
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
     * @param chatId Chat

     */
    private void sendCurrentWeather(Long chatId, String locationKey) {
        TelegramBotService telegramBotService = new TelegramBotService(new BotConfig());
        try {

            CurrentWeather currentWeather = getCurrentWeather(locationKey);

            double temperature = currentWeather.getTemperatureCurrent().getTempMetricCurrent().getValue();
            String weatherText = getWeatherText(currentWeather, temperature);


            telegramBotService.prepareAndSendMessage(chatId, weatherText);
        } catch(Exception e) {
            //logger.error("Weather error", e);
            telegramBotService.prepareAndSendMessage(chatId, "weather_get_error");
        }
    }

    private static String getWeatherText(CurrentWeather currentWeather, double temperature) {
        String unit = String.format(currentWeather.getTemperatureCurrent().getTempMetricCurrent().getUnit());
        String localDateTime = String.format(currentWeather.getLocalDateTime());
        boolean isDayTime = currentWeather.isDayTime();
        if (isDayTime) {
            //выводим, что сейчас день
        }
        else {
            //выводим, что сейчас ночь
        }
        String link = String.format(currentWeather.getLink());

        String weatherText = String.format("Weather now:\n Date: %tT \n %S \n Temperature: %g %S \n Resource: %S",
                 localDateTime,
                 currentWeather.getWeatherText(),
                temperature, unit, link);
        return weatherText;
    }

    private JSONObject getDailyWeatherObject(String locationKey) throws Exception {
        HttpResponse<JsonNode> response = Unirest.get(WEATHER_URL_DAILY)
                .routeParam("locationKey", locationKey)
                .queryString("apiKey", API_KEY)
                .asJson();
        return response.getBody().getObject();
    }

    private WeatherForecastOneDay getDailyWeather(String cityName) throws Exception {
        try {
            JSONObject weatherObject = getDailyWeatherObject(gettinglocationKey(cityName));
            WeatherForecastOneDay weather = JsonUtil.toObject(weatherObject, WeatherForecastOneDay.class);
            if(weather == null) {
                throw new Exception("Cannot parse weather");
            }
            return weather;
        } catch(Exception e) {
            //logger.error("Cannot get weather data", e);
            throw e;
        }
    }

    private void sendDailyWeather(Long chatId, String locationKey) {
        TelegramBotService telegramBotService = new TelegramBotService(new BotConfig());
        try {

            WeatherForecastOneDay dailyWeather = getDailyWeather(locationKey);
            for (DailyForecasts forecasts : dailyWeather.getDailyForecasts()) {


                String weatherText = getWeatherText(forecasts);


                telegramBotService.prepareAndSendMessage(chatId, weatherText);
            }
        } catch(Exception e) {
            //logger.error("Weather error", e);
            telegramBotService.prepareAndSendMessage(chatId, "weather_get_error");
        }
    }

    private static String getWeatherText(DailyForecasts forecasts) {
        double temperatureMin = forecasts.getTemperatureForecast().getMinimumTemperature().getValue();
        double temperatureMax = forecasts.getTemperatureForecast().getMaximumTemperature().getValue();
        String temperatureMinUnit = String.format(forecasts.getTemperatureForecast().getMinimumTemperature().getUnit());
        String temperatureMaxUnit = String.format(forecasts.getTemperatureForecast().getMaximumTemperature().getUnit());
        String dailyForecastDate = String.format(forecasts.getDailyForecastsDate());


        String weatherText = String.format("Daily forecast:\n Date: %S  \n Temperature(max): %g %S \n Temperature(min): %g %S",
                dailyForecastDate,
                temperatureMax, temperatureMaxUnit, temperatureMin, temperatureMinUnit);
        return weatherText;
    }

    private void LocationToUser(Update userAnswer){
        cityAsk();
        TelegramBotService.cityUserAnswer(userAnswer);// TODO надо импортировать сюда telegrambots или перенести этот класс
        String cityName=null;//TODo заменить на строчный ответ пользователя
        cityRequest(cityName);


    }

    private void CurrentWeatherToUser(Long chatId, String locationKey){
        LocationToUser();//TODO посмотреть, что именно должен принимать класс, и что такое Update
        sendCurrentWeather(chatId, locationKey);
    }

    private void DailyWeatherToUser(Long chatId, String locationKey){
        LocationToUser();
        sendDailyWeather(chatId, locationKey);
    }

    //TODO посмотреть в видео Финашкина, как программируются действия кнопок при их нажатии
}


