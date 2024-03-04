package com.project.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
@Data
public class DailyForecast {

    @JsonProperty("Date")
    private String DailyForecastsDate;



    @JsonProperty("Temperature")
    private TemperatureForecast temperatureForecast;
}
