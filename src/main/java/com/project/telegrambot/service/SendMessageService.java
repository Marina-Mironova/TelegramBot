package com.project.telegrambot.service;

import com.project.telegrambot.config.BotConfig;
import com.vdurmont.emoji.EmojiParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.project.telegrambot.service.TelegramBotService.ERROR_TEXT;
//import static com.project.telegrambot.service.TelegramBotService.WEATHER_NOW;

@Slf4j
@Data
@Component
public class SendMessageService {

//    TelegramBotService telegramBotService = new TelegramBotService(new BotConfig());
//    Message message = new Message();
//    private ThreadLocal<Update> updateEvent = new ThreadLocal<>();

//    public SendMessage createMessage( String text) {
//        SendMessage message = new SendMessage();
//        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
//        message.setParseMode("markdown");
//        Long chatId = getCurrentChatId();
//        message.setChatId(chatId);
//        return message;
//    }

//    public void sendTextMessageAsync(String text) {
//        SendMessage message = createMessage(text);
//        telegramBotService.sendApiMethodAsync(message);
//    }

//    public Long getCurrentChatId() {
//        if (updateEvent.get().hasMessage()) {
//            return updateEvent.get().getMessage().getFrom().getId();
//        }
//
//        if (updateEvent.get().hasCallbackQuery()) {
//            return updateEvent.get().getCallbackQuery().getFrom().getId();
//        }
//
//        return null;
//    }
//
//    public String getMessageText() {
//        return updateEvent.get().hasMessage() ? updateEvent.get().getMessage().getText() : "";
//    }
//
//    private void stopChat(long chatId) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId);
//        sendMessage.setText("Thank you for your interest to this bot. See you soon!\nPress /start to order again");
//        //chatStates.remove(chatId);
//        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
//        //   sender.execute(sendMessage);
//    }
//
//
//    public String handleUpdate(Update update) throws IOException, InterruptedException {
//        String messageText;
//        Long chatId;
//        String userFirstName = "";
//
//        //если сообщение пришло в лс боту
//        if (update.hasMessage()) {
//            chatId = update.getMessage().getChatId();
//            messageText = update.getMessage().getText().toUpperCase(Locale.ROOT).replace("/", "");
//            userFirstName = update.getMessage().getChat().getFirstName();
//            return messageText;
//        }
//
//
//
//        return "no city";
//    }
//
//
//    private void executeEditMessageText(long chatId,String text, long messageId){
//        EditMessageText message = new EditMessageText();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(text);
//        message.setMessageId((int) messageId);
//        try {
//            telegramBotService.execute(message);
//        }
//        catch (TelegramApiException e) {
//            log.error(ERROR_TEXT + e.getMessage());
//        }
//    }
//
//
//
//
//
//    public void prepareAndSendMessage(Long chatId, String textToSend) {
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(textToSend);
//        telegramBotService.executeMessage(message);
//    }


//    public void startCommandReceived(long chatId, String name){
//
//        String answer = EmojiParser.parseToUnicode("Hi, " + name + ", nice to meet you!" + " :blush:");
//        log.info("Replied to user " + name);
//        sendMessage(chatId, answer);
//    }
//
//    public void sendMessage(long chatId, String textToSend) {
//        KeyboardButtonService keyboardButtonService = new KeyboardButtonService();
//
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(textToSend);
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
//        // message.setReplyMarkup(keyboardMarkup);
//        message.setReplyMarkup(keyboardMarkup);
//       // message.setReplyMarkup(keyboardMarkup);
//
//        telegramBotService.executeMessage(message);
//    }



}
