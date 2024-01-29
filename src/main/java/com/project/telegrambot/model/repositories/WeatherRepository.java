package com.project.telegrambot.model.repositories;

import com.project.telegrambot.model.entities.User;
import com.project.telegrambot.model.entities.Weather;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<Weather, Long> {
}
