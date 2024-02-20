package com.project.telegrambot.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentWeather {

    private String LocalObservationDateTime;

    private String WeatherText;

    private int WeatherIcon;

    private boolean IsDayTime;

    private double TemperatureMetricValue;

    private String TemperatureMetricUnit;

    private Object RealFeelTemperature;

    private String WindDirectionEnglish;

    private Object WindSpeed;

    private Object Pressure;

    private String Link;
}
