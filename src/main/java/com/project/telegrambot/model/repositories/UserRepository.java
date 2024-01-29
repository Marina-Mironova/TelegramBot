package com.project.telegrambot.model.repositories;

import com.project.telegrambot.model.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
