package com.project.telegrambot.service;

import com.project.telegrambot.utils.Action;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class NewCityAction implements Action {
    @Override
    public BotApiMethod handle(Update update) {
        var msg = update.getMessage();
        var chatId = msg.getChatId().toString();
        var text = "Type the name of the city: ";
        return new SendMessage(chatId, text);
    }

    @Override
    public BotApiMethod callback(Update update) {
        var msg = update.getMessage();
        var chatId = msg.getChatId().toString();
        var city = msg.getText();
        // userRepository.save(new User(email));
        var text = "Your chosen city is: " + city;
        return new SendMessage(chatId, text);
    }
}