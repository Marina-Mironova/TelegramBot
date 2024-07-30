package com.project.telegrambot.service;

import kong.unirest.core.JsonNode;
import kong.unirest.core.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    WeatherService weatherService = new WeatherService();
    String jsonStringCurrent = "[{\"LocalObservationDateTime\":\"2024-05-13T13:12:00+02:00\",\"EpochTime\":1715598720,\"WeatherText\":\"Sunny\",\"WeatherIcon\":1,\"HasPrecipitation\":false,\"PrecipitationType\":null,\"IsDayTime\":true,\"Temperature\":{\"Metric\":{\"Value\":22.4,\"Unit\":\"C\",\"UnitType\":17},\"Imperial\":{\"Value\":72.0,\"Unit\":\"F\",\"UnitType\":18}},\"MobileLink\":\"http://www.accuweather.com/en/de/velten/16727/current-weather/167930?lang=en-us\",\"Link\":\"http://www.accuweather.com/en/de/velten/16727/current-weather/167930?lang=en-us\"}]";



    @Test
    void locationRequestTestNullArg() {
        weatherService.locationRequest(null);
        assertEquals(Optional.empty(), Optional.empty());
    }



    @Test
    void testLocationRequest() {
    }


    @Test
    void getLocationKeyStringTestNullArg() throws Exception {

        assertThrows(NullPointerException.class, () -> weatherService.getLocationKeyString(null));

    }

    @Test
    void getLocationKeyStringTestJSONException() throws Exception {
  }

    @Test
    void getLocalisedNameStringTestNullArg() throws Exception {
        assertThrows(NullPointerException.class, () -> weatherService.getLocalisedNameString(null));
    }

    @Test
    void getCurrentWeatherObjectTestNullArg() {
        weatherService.getCurrentWeatherObject(null);
        assertEquals(Optional.empty(), Optional.empty());
    }

    @Test
    void getCurrentWeatherObjectList() {
    }

    @Test
    void getWeatherTextString() {
    }

    @Test
    void getIsDayTimeTestNullArg() throws Exception {

    }

    @Test
    void getLink() {
    }

    @Test
    void getCurrentWeatherTemperature() {
    }

    @Test
    void sendCurrentWeatherText() {
    }

    @Test
    void getDailyWeatherObject() {
    }

    @Test
    void getDailyWeatherObjectList() {
    }

    @Test
    void getDailyWeatherHeadlineString() {
    }

    @Test
    void getDailyWeatherForecastStringDate() {
    }

    @Test
    void getDailyWeatherForecastTemperature() {
    }

    @Test
    void getDailyWeatherTempMin() {
    }

    @Test
    void getDailyWeatherTempMinValue() {
    }

    @Test
    void getDailyWeatherTempMinUnit() {
    }

    @Test
    void getDailyWeatherTempMax() {
    }

    @Test
    void getDailyWeatherTempMaxValue() {
    }

    @Test
    void getDailyWeatherTempMaxUnit() {
    }

    @Test
    void getDailyWeatherDay() {
    }

    @Test
    void getDailyWeatherDayPhrase() {
    }

    @Test
    void getDailyWeatherNight() {
    }

    @Test
    void getDailyWeatherNightPhrase() {
    }

    @Test
    void sendWeatherForecast() {
    }

    @Test
    void sendCurrentWeather() {
    }

    @Test
    void sendDailyWeather() {
    }
}