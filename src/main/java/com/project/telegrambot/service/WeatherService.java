package com.project.telegrambot.service;

import com.project.telegrambot.config.BotConfig;
import com.project.telegrambot.controller.JsonUtil;
import com.project.telegrambot.dto.*;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

import static com.project.telegrambot.service.TelegramBotService.ERROR_TEXT;


//import org.json.JSONObject;

@Service
@Slf4j
public class WeatherService {

    //final WeatherMain weatherMain = new WeatherMain();

    private String LOCATION_URL = "http://dataservice.accuweather.com/locations/v1/cities/autocomplete";// weatherMain.getACCU_WEATHER_LOCATION_URL();
    private String API_KEY = "Rjr1HRBdhAMmGhoPPD1V36xrmx30Cpjw"; ;//weatherMain.getACCU_WEATHER_API_KEY();
    private String WEATHER_URL_NOW = "http://dataservice.accuweather.com/currentconditions/v1/{locationKey}" ;//weatherMain.getACCU_WEATHER_URL_NOW();

    private String WEATHER_URL_DAILY = " http://dataservice.accuweather.com/forecasts/v1/daily/1day/{locationKey}";//weatherMain.getACCU_WEATHER_URL_DAILY();

//    public WeatherMain getWeatherMain() {
//         return this.weatherMain;
//
//    }


//    public String getACCU_WEATHER_LOCATION_URL() {
//
//        return weatherMain.getACCU_WEATHER_LOCATION_URL();
//    }


//    public String getgetACCU_WEATHER_API_KEY() {
//
//        return weatherMain.getACCU_WEATHER_API_KEY();
//    }


//    public String getACCU_WEATHER_URL_DAILY() {
//
//        return weatherMain.getACCU_WEATHER_URL_DAILY();
//    }
//
//
//    public String getACCU_WEATHER_URL_NOW() {
//
//        return weatherMain.getACCU_WEATHER_URL_NOW();
//    }


    public JSONObject locationRequest(String cityName) {

    try {

     HttpResponse<JsonNode> response = Unirest.get(LOCATION_URL)
             .queryString("apiKey", API_KEY)
             .queryString("q", cityName)
             .asJson();
     return response.getBody().getObject();

    } catch (NullPointerException e) {
        log.error("Null pointer exception error " +e.getMessage());
        throw e;
    }

 }


    Location getLocationObject(String cityName) throws Exception {
       try {
       JSONObject locationObject =locationRequest(cityName);
      Location location = JsonUtil.toObject(locationObject, Location.class);
        //   Location location = locationRequest(cityName);//.getBody();// если сделать, чтобы предыдущий метод выводил тем же способом локацию, то этот будет уже не нужен
       if(location == null) {
           throw new Exception("Cannot parse location");
       }
       return location;
       } catch(Exception e) {
           log.error(ERROR_TEXT + e.getMessage()+"Cannot get weather data");
           throw e;
       }


   }

    static String getLocationKeyString(Location location) {
        String locationKey = String.format(location.getLocationKey());


        return locationKey;
    }
     static String getLocalisedNameString(Location location) {

        String localisedName = String.format(location.getLocalisedName());

        return localisedName;
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
            String localisedName = getLocalisedNameString(getLocationObject(cityName));
            JSONObject weatherObject = getCurrentWeatherObject(getLocationKeyString(getLocationObject(localisedName)));
            CurrentWeather weather = JsonUtil.toObject(weatherObject, CurrentWeather.class);
            if(weather == null) {
                throw new Exception("Cannot parse weather");
            }
            return weather;
        } catch(Exception e) {
            log.error(ERROR_TEXT + e.getMessage());
            throw e;
        }
    }



    /**
     * Send weather in city to chat
     * @param chatId Chat

     */
    void sendCurrentWeather(Long chatId, String locationKey) {
        TelegramBotService telegramBotService = new TelegramBotService(new BotConfig());
        try {

            CurrentWeather currentWeather = getCurrentWeather(getLocalisedNameString(new Location()));

            double temperature = currentWeather.getTemperatureCurrent().getTempMetricCurrent().getValue();
            String weatherText = getWeatherText(currentWeather, temperature);


            telegramBotService.prepareAndSendMessage(chatId, weatherText);
        } catch(Exception e) {
            log.error(ERROR_TEXT + e.getMessage());
            telegramBotService.prepareAndSendMessage(chatId, "weather_get_error");
            //telegramBotService.prepareAndSendMessage(chatId, "weather_get_error");
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
            String localisedName = getLocalisedNameString(getLocationObject(cityName));
            JSONObject weatherObject = getDailyWeatherObject(getLocationKeyString(getLocationObject(localisedName)));
            WeatherForecastOneDay weather = JsonUtil.toObject(weatherObject, WeatherForecastOneDay.class);
            if(weather == null) {
                throw new Exception("Cannot parse weather");
            }
            return weather;
        } catch(Exception e) {
            log.error(ERROR_TEXT + e.getMessage());
            throw e;
        }
    }

     void sendDailyWeather(Long chatId, String locationKey) {
        TelegramBotService telegramBotService = new TelegramBotService(new BotConfig());
        try {

            WeatherForecastOneDay dailyWeather = getDailyWeather(getLocalisedNameString(new Location()));
            for (DailyForecasts forecasts : dailyWeather.getDailyForecasts()) {


                String weatherText = getWeatherText(forecasts);


                telegramBotService.prepareAndSendMessage(chatId, weatherText);
            }
        } catch(Exception e) {
            log.error(ERROR_TEXT + e.getMessage());
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
       //cityAsk();
      //  TelegramBotService.cityUserAnswer(userAnswer);
      //  cityRequest(cityName);


    }

    private void CurrentWeatherToUser(Long chatId, String locationKey){
      //  LocationToUser();
        sendCurrentWeather(chatId, locationKey);
    }

    private void DailyWeatherToUser(Long chatId, String locationKey){
       // LocationToUser();
        sendDailyWeather(chatId, locationKey);
    }


}


