package com.project.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Data
public class DailyForecast {

    @JsonProperty
    private String DailyForecastsDate;

    @JsonProperty
    private String DailyForecastsSunRise;

    @JsonProperty
    private String DailyForecastsSunSet;

    @JsonProperty
    private List<TemperatureForecast> Temperature;
}
