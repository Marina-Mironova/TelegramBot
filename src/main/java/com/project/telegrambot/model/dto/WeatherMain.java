package com.project.telegrambot.model.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherMain {
    @Value("${weather.now.url}")
    private String ACCU_WEATHER_URL_NOW;

    @Value("${weather.key}")
    private String ACCU_WEATHER_API_KEY;

    @Value("${weather.daily.forecast.url}")
    private String ACCU_WEATHER_URL_DAILY;

    @Value("${location.key}")
    private String ACCU_WEATHER_LOCATION_KEY;
}
