package com.project.telegrambot.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
@PropertySource("application.properties")
public class WeatherMain {
    @Value("${weather.now.url}")
    private String ACCU_WEATHER_URL_NOW;

    @Value("${weather.key}")
    private String ACCU_WEATHER_API_KEY;

    @Value("${weather.daily.forecast.url}")
    private String ACCU_WEATHER_URL_DAILY;

    @Value("${weather.location.url}")
    private String ACCU_WEATHER_LOCATION_URL;



    protected boolean canEqual(final Object other) {
        return other instanceof WeatherMain;
    }

}
