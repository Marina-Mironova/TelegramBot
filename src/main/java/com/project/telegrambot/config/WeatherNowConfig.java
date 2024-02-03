package com.project.telegrambot.config;

import lombok.Data;
import lombok.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
public class WeatherNowConfig {

    @Value("${weather.key}")
    String apikey;

    @Value("${weather.language}")
    String language;


}
