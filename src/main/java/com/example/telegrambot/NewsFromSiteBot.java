package com.example.telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class NewsFromSiteBot extends TelegramLongPollingBot {


    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        // TODO
        return "newsFromSiteBot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "6098183432:AAH9IfwhEApVtPlhFzVbt5i3fw5P_Wr41fI";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}

