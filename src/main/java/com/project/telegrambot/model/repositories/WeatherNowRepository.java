package com.project.telegrambot.model.repositories;


import com.project.telegrambot.model.entities.WeatherNow;
import org.springframework.data.repository.CrudRepository;

public interface WeatherNowRepository extends CrudRepository<WeatherNow, Long> {
}
