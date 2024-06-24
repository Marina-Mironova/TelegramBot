//package com.project.telegrambot.config.weather;
//
//import com.project.telegrambot.service.TelegramBotService;
//import com.project.telegrambot.service.WeatherService;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//
//
//@Slf4j
//@Data // Это аннотация lombok, которая автоматически генерирует getter-ы и setter-ы
//@Configuration // Аннотация Spring, благодаря которой автоматически будет создан been
//@ConfigurationProperties(prefix = "application.weather")
//public class WeatherConfig {
//    @Value("${weather.now.url}")
//    private String weatherUrlNow;
//
//    @Value("${weather.key}")
//    private String apiKey;
//
//    @Value("${weather.daily.forecast.url}")
//    private String weatherUrlDaily;
//
//    @Value("${weather.location.url}")
//    private String locationUrl;
//
//    @Value("${weather.auth.pass}")
//    private String weatherPass;
//
//    @Value("${weather.auth.email}")
//    private String weatherEmail;
//
//}
//
