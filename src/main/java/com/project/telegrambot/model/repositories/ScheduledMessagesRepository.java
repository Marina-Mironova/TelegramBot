package com.project.telegrambot.model.repositories;

import com.project.telegrambot.model.entities.ScheduledMessages;
import org.springframework.data.repository.CrudRepository;

public interface ScheduledMessagesRepository extends CrudRepository<ScheduledMessages, Long>{

}
