package com.project.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Data
public class CurrentWeather {

    @JsonProperty("LocalObservationDateTime")
    private String LocalDateTime;

    @JsonProperty("WeatherText")
    private String weatherText;

    @JsonProperty("IsDayTime")
    private boolean isDayTime;

    @JsonProperty("Temperature")
    private TemperatureCurrent temperatureCurrent;

    @JsonProperty("Link")
    private String link;
}
