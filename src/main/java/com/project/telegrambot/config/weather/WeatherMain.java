package com.project.telegrambot.config.weather;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
@ConfigurationProperties(prefix = "weather")
public class WeatherMain {
    @Value("${weather.now.url:test name}")
   public static String weatherUrlNow;

    @Value("${weather.key:test key}")
    public static String apiKey;

    @Value("${weather.daily.forecast.url:test url}")
    public static String weatherUrlDaily;

    @Value("${weather.location.url:test url}")
    public static String locationUrl;

    @Value("${weather.auth.pass:test pass}")
    public static String weatherPass;

    @Value("${weather.auth.email}")
    public static String weatherEmail;
//
// public WeatherMain(String weatherUrlNow, String apiKey, String weatherUrlDaily, String locationUrl, String weatherPass, String weatherEmail) {
//  this.weatherUrlNow = weatherUrlNow;
//  this.apiKey = apiKey;
//  this.weatherUrlDaily = weatherUrlDaily;
//  this.locationUrl = locationUrl;
//  this.weatherPass = weatherPass;
//  this.weatherEmail = weatherEmail;
// }
}
