package com.project.telegrambot.service;

import com.project.telegrambot.config.bot.BotConfig;
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


@Data
@Slf4j
@Component
public class TelegramBotService extends TelegramLongPollingBot {


    private String MESSAGE_TEXT = "";
    @Autowired
    private UserRepository userRepository;


    final BotConfig config;

    final String HELP_TEXT = "Here should be help for using this bot.";

    static final String YES_BUTTON = "YES";
    static final String NO_BUTTON = "NO";
    String CALLBACK_CITY = null;

    static final String ERROR_TEXT = "Error occurred: ";

    boolean isCallback = false;

    private List<Message> sendMessages = new ArrayList<>();






    /**
     * TelegramBotService Method
     * - constructor of this class
     * with the list of usable commands
     * @param config
     */

    public TelegramBotService(BotConfig config) {


        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "register and get a welcome message"));
        listOfCommands.add(new BotCommand("/mydata", "get your data stored"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        listOfCommands.add(new BotCommand("/weathernow", "current weather"));
        listOfCommands.add(new BotCommand("/dailyweather", "weather info for the next day"));
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
            messageHasText(update);
      } else {
            callbackQueryExists(update);
        }

    }


    /**
     * registerUser Method
     * - user registration to the database
     * @param msg
     */
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


    /**
     * prepareAndSendMessage Method
     * - sends any message to the chat
     * @param chatId
     * @param textToSend
     */
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

    /**
     * menuBot Method
     * - Bot commands menu with their actions
     * @param messageText
     * @param chatId
     * @param update
     * @throws Exception
     */
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

            case "/weathernow", "weather now":

                currentWeatherCommand(chatId);
                break;

            case "/dailyweather", "weather for 1 day":

                dailyWeatherCommand(chatId);


                break;


            default:

                prepareAndSendMessage(chatId, "Sorry, command was not recognized");
                break;
        }

    }


    /**
     * dailyWeatherCommand Method
     * - it sends weather forecast for the next day to the bot
     * @param chatId
     */
    private void dailyWeatherCommand (Long chatId){
        WeatherService weatherService = new WeatherService();
        prepareAndSendMessage(chatId, "Here is weather for tomorrow.");
        if (!isCallback) {
            cityChoose(chatId);
        }

        if (CALLBACK_CITY != null) {
            prepareAndSendMessage(chatId, weatherService.sendDailyWeather(CALLBACK_CITY));
        }
        else {
            prepareAndSendMessage(chatId, "Press the button and wait a second...");
        }

    }

    /**
     * currentWeatherCommand Method
     * - it sends current weather info to the bot
     * @param chatId
     */
    private void currentWeatherCommand(Long chatId){
        WeatherService weatherService = new WeatherService();
        prepareAndSendMessage(chatId, "Here is weather for today.");
        if (!isCallback){
            cityChoose(chatId);
        }

        if (CALLBACK_CITY != null) {
            prepareAndSendMessage(chatId, weatherService.sendCurrentWeather(CALLBACK_CITY));
        }
        else {
            prepareAndSendMessage(chatId, "Press the button and wait a second...");
        }
    }

    /**
     * register Method
     * - send message asking user.
     * if he really wants to register.
     * Creates inline keyboard with "yes" and "no" buttons.
     * @param chatId
     */
    private void register(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Do you really want to register?");

        InlineKeyboardService inlineKeyboardService = new InlineKeyboardService();
        inlineKeyboardService.registerInlineKeyboard();

        executeMessage(message);
    }

    /**
     * startCommandReceived Method
     * - bot reaction, when command "/start" getting
     * @param chatId
     * @param name
     * @throws Exception
     */
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

    /**
     * cityChoose Method
     * - send message with question,
     * ask choosing city from inline keyboard buttons
     * @param chatId
     */
    private void cityChoose(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Which city do you need?");

        InlineKeyboardService inlineKeyboardService = new InlineKeyboardService();
        ReplyKeyboard markupInLine = inlineKeyboardService.setInlineCities();
        message.setReplyMarkup(markupInLine);
        executeMessage(message);
    }

    private void callbackQueryExists(Update update){
        if (update.hasCallbackQuery()) {

            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();


            prepareAndSendMessage(chatId, "Your city is " + callbackData);
            CALLBACK_CITY = callbackData;
            isCallback = true;
            prepareAndSendMessage(chatId, "Now I prepare the weather forecast for you. City: " + CALLBACK_CITY + ". Please wait.");
            prepareAndSendMessage(chatId, "The city is " + CALLBACK_CITY);
            try {
                menuBot(MESSAGE_TEXT, chatId, update);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            isCallback = false;
            CALLBACK_CITY = null;
            // = null;
        }
    }

    private void messageHasText(Update update){
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        prepareAndSendMessage(chatId, "Thank you!");
        MESSAGE_TEXT = messageText;
        try {
            menuBot(messageText, chatId, update);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    private void deleteMe(Message msg){
//        if (userRepository.findById(msg.getChatId()).isPresent()) {
//
//            var chatId = msg.getChatId();
//            var chat = msg.getChat();
//
//            User user = User();
//
//            userRepository.delete(user);
//            log.info("user deleted: " + user);
//        }
//        else {
//            prepareAndSendMessage(msg.getChatId(), "You are not registered.");
//        }
//    }

}

