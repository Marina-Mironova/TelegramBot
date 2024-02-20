package com.project.telegrambot.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyForecast {


    private String DailyForecastsDate;

    private String DailyForecastsSunRise;

    private String DailyForecastsSunSet;

    private double DailyForecastsTemperatureMinimumValue;

    private String DailyForecastsTemperatureMinimumUnit;

    private double DailyForecastsTemperatureMaximumValue;

    private String  DailyForecastsTemperatureMaximumUnit;
}
