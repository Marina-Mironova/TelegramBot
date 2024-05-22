package com.project.telegrambot.service;

import com.project.telegrambot.config.BotConfig;
import com.project.telegrambot.dto.Location;
import com.project.telegrambot.model.entities.User;
import com.project.telegrambot.model.repositories.UserRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.project.telegrambot.service.KeyboardButtonService.getReplyKeyboardMarkup;
import static com.project.telegrambot.service.WeatherService.sendCurrentWeather;
import static com.project.telegrambot.service.WeatherService.sendDailyWeather;

@Data
@Slf4j
@Component
public class TelegramBotService extends TelegramLongPollingBot {


    @Autowired
    private UserRepository userRepository;

    final BotConfig config;

    static final String HELP_TEXT = "Here should be help for using this bot.";

    static final String YES_BUTTON = "YES_BUTTON";
    static final String NO_BUTTON = "NO_BUTTON";
    String CALLBACK_CITY = null;

    static final String ERROR_TEXT = "Error occurred: ";

    private List<Message> sendMessages = new ArrayList<>();


    public TelegramBotService(BotConfig config) {


        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "register and get a welcome message"));
        listOfCommands.add(new BotCommand("/mydata", "get your data stored"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        listOfCommands.add(new BotCommand("/weathernow", "current weather"));
        listOfCommands.add(new BotCommand("/dailyweather", "weather info for the next day"));
        listOfCommands.add(new BotCommand("/echo", "data typing for command"));
        listOfCommands.add(new BotCommand("/set_city", "set city for the weather forecast"));
        //listOfCommands.add(new BotCommand("/stop", "stop sending new rss to you"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));

        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public String getBotUsername() {

        return config.getBotName();
    }

    @Override
    public String getBotToken() {

        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            prepareAndSendMessage(chatId, "Thank you!");

            try {
                menuBot(messageText, chatId, update);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery()) {

            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals("Velten")) { // TODO: внести изменения для разных погодных команд
                prepareAndSendMessage(chatId, "Your city is " + callbackData);
                CALLBACK_CITY = callbackData;
                prepareAndSendMessage(chatId, "Now I prepare the weather forecast for you. City: " + CALLBACK_CITY + ". Please wait.");
                prepareAndSendMessage(chatId, "The city is " + CALLBACK_CITY);
//                try {
//                    //prepareAndSendMessage(chatId, sendCurrentWeather(CALLBACK_CITY));
//                    menuBot(, chatId, update);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }


            }
            if (callbackData.equals("Berlin")) {
                prepareAndSendMessage(chatId, "Your city is " + callbackData);
                CALLBACK_CITY = callbackData;
                prepareAndSendMessage(chatId, "Now I prepare the weather forecast for you. City: " + CALLBACK_CITY + ". Please wait.");
                try {
                    prepareAndSendMessage(chatId, sendCurrentWeather(CALLBACK_CITY));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }


    private void registerUser(Message msg) {
        if (userRepository.findById(msg.getChatId()).isEmpty()) {

            var chatId = msg.getChatId();
            var chat = msg.getChat();

            User user = new User();

            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);
            log.info("user saved: " + user);
        }
    }


    public void prepareAndSendMessage(Long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }

    public void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

    private void menuBot(String messageText, long chatId, Update update) throws Exception {
        switch (messageText) {
            case "/start":

                registerUser(update.getMessage());
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                break;

            case "/help":

                prepareAndSendMessage(chatId, HELP_TEXT);
                break;

            case "/register":

                register(chatId);
                break;

            case "/weathernow":

                currentWeatherCommand(chatId);
                break;

            case "/dailyweather":

                dailyWeatherCommand(chatId);


                break;


            case "weather now":
                currentWeatherCommand(chatId);



                break;

            case "weather for 1 day":
                dailyWeatherCommand(chatId);

                break;

            default:

                prepareAndSendMessage(chatId, "Sorry, command was not recognized");
                break;
        }

    }

    private void dailyWeatherCommand (Long chatId){
        prepareAndSendMessage(chatId, "Here is weather for tomorrow.");
        cityChoose(chatId);
        prepareAndSendMessage(chatId, sendDailyWeather(CALLBACK_CITY));
        //      dailyWeatherCommand(chatId, CALLBACK_CITY);
    }

    private void currentWeatherCommand(Long chatId){
        prepareAndSendMessage(chatId, "Here is weather for today.");
        cityChoose(chatId);


        prepareAndSendMessage(chatId, sendCurrentWeather(CALLBACK_CITY));
//               prepareAndSendMessage(chatId, "The city is " + CALLBACK_CITY);
    }

    private void register(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Do you really want to register?");

        InlineKeyboardService inlineKeyboardService = new InlineKeyboardService();
        inlineKeyboardService.registerInlineKeyboard();

        executeMessage(message);
    }

    public void startCommandReceived(long chatId, String name) throws Exception {

        String answer = EmojiParser.parseToUnicode("Hi, " + name + ", nice to meet you!" + " :blush:");
        log.info("Replied to user " + name);
        sendMessage(chatId, answer);
    }

    public void sendMessage(long chatId, String textToSend) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        ReplyKeyboardMarkup keyboardMarkup = getReplyKeyboardMarkup();

        message.setReplyMarkup(keyboardMarkup);
        executeMessage(message);
    }

    private void cityChoose(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Which city do you need?");

        InlineKeyboardService inlineKeyboardService = new InlineKeyboardService();
        ReplyKeyboard markupInLine = inlineKeyboardService.setInlineCities();
        message.setReplyMarkup(markupInLine);
        executeMessage(message);
    }


    protected boolean canEqual(final Object other) {
        return other instanceof TelegramBotService;
    }

}
