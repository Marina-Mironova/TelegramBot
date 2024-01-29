package com.project.telegrambot.model.repositories;

import com.project.telegrambot.model.entities.Coordinates;
import com.project.telegrambot.model.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface CoordinatesRepository extends CrudRepository<Coordinates, Long> {
}
