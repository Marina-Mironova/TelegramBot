package com.project.telegrambot.service;

import com.project.telegrambot.config.BotConfig;
import com.project.telegrambot.model.entities.ScheduledMessages;
import com.project.telegrambot.model.entities.User;
import com.project.telegrambot.model.repositories.ScheduledMessagesRepository;
import com.project.telegrambot.model.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class ScheduledMessageService extends TelegramBotService{

  //  @Autowired
    private UserRepository userRepository;

   // @Autowired
    private ScheduledMessagesRepository scheduledMessagesRepository;

    public ScheduledMessageService(BotConfig config) {
        super(config);
    }

    @Scheduled(cron = "${cron.scheduler}")
    private void sendScheduledMessages(){
        SendMessageService sendMessageService = new SendMessageService();
        var scheduledMessages = scheduledMessagesRepository.findAll();
        var users = userRepository.findAll();

        for(ScheduledMessages schMessage: scheduledMessages) {
            for (User user: users){
                sendMessageService.prepareAndSendMessage(user.getChatId(), schMessage.getScheduledMessage());
            }
        }
    }
}
