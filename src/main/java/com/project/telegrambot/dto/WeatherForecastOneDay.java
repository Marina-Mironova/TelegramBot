package com.project.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@JsonIgnoreProperties
public class WeatherForecastOneDay {

    @JsonProperty("DailyForecasts")
    private List<DailyForecast> dailyForecast;
}
