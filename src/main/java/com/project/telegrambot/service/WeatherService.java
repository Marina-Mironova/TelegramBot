package com.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    private static final String ACCU_WEATHER_URL = "";

    @Value("${weather.key}")
    private String ACCU_WEATHER_API_KEY;
}
