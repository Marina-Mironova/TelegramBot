package com.project.telegrambot;

import com.project.telegrambot.config.weather.WeatherMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TelegramBotApplication {

	public static void main(String[] args) {
		 SpringApplication.run(TelegramBotApplication.class, args);
//		ApplicationContext context = SpringApplication.run(SpringPropertiesApplication.class, args);
//		System.out.println(context.getBean(WeatherMain.class).getWeatherUrlNow());
		System.out.println(WeatherMain.weatherEmail);
	}

}

