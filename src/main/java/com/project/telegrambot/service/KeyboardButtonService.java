package com.project.telegrambot.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.project.telegrambot.service.TelegramBotService.WEATHER_NOW;

@Slf4j
@Data
@Component
public class KeyboardButtonService {

//    Message message = new Message();
//
//    public void setKeyboardRows() {
//        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//        //InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        List<KeyboardRow> keyboardRows = new ArrayList<>();
//        KeyboardRow row = new KeyboardRow();
//
//        //var weatherNowButton = new KeyboardButton;
//
//        // weatherNowButton.setText("weather now");
////weatherNowButton.setCallbackData(WEATHER_NOW);
//
//        row.add(WEATHER_NOW);
//        row.add("weather forecast for 1 day");
//
//        keyboardRows.add(row);
//
//        row = new KeyboardRow();
//
//
//        keyboardRows.add(row);
//
//        keyboardMarkup.setKeyboard(keyboardRows);
//
//       // message.setReplyMarkup(keyboardMarkup);
//        message.setReplyMarkup(keyboardMarkup);
//
//    }
//
//}



}
