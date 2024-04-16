package com.project.telegrambot.config;

import com.project.telegrambot.utils.Action;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableScheduling
@Data
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String token;

    @Value("${bot.owner}")
    Long ownerId;

    Map<String, String> bindingBy = new ConcurrentHashMap<>();

    Map<String, Action> actions;


}
