package com.project.telegrambot.service;

import com.project.telegrambot.config.BotConfig;
import com.project.telegrambot.dto.Location;
import com.project.telegrambot.model.entities.User;
import com.project.telegrambot.model.repositories.UserRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.lang.Thread.sleep;


@Slf4j
@Data
@Component
public class TelegramBotService extends TelegramLongPollingBot {


    @Autowired
    private UserRepository userRepository;

    private ThreadLocal<Update> updateEvent = new ThreadLocal<>();

    private SendMessageService sendMessageService = new SendMessageService();



    final BotConfig config;

    static final String HELP_TEXT = "Here should be help for using this bot.";

    static final String YES_BUTTON = "YES_BUTTON";
    static final String NO_BUTTON = "NO_BUTTON";
    static final String WEATHER_NOW = "weather now";

    static final String ERROR_TEXT = "Error occurred: ";

    public TelegramBotService(BotConfig config) {



        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "register and get a welcome message"));
        listOfCommands.add(new BotCommand("/mydata", "get your data stored"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        listOfCommands.add(new BotCommand("/weathernow", "current weather"));
        listOfCommands.add(new BotCommand("/dailyweather", "weather info for the next day"));
         //listOfCommands.add(new BotCommand("/stop", "stop sending new rss to you"));
        try{
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));

        }
        catch (TelegramApiException e) {
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

           /* if(messageText.contains("/send") && config.getOwnerId() == chatId) {
                sendToAll(messageText);
            }

            else {*/

                try {
                    menuBot(messageText, chatId, update);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

        }
        else if(update.hasCallbackQuery()){


            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

                if(callbackData.equals("weather now")) {
                    try {
                        currentWeatherCommand(chatId,update);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                } else if (callbackData.equals("weather forecast for 1 day")) {

                    try {
                        dailyWeatherCommand(chatId, update);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }



        /*else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if(callbackData.equals(YES_BUTTON)){
                String text = "You pressed YES button";
                executeEditMessageText(chatId, text, messageId);
            }
            else if(callbackData.equals(NO_BUTTON)){
                String text = "You pressed NO button";
                executeEditMessageText(chatId, text, messageId);
            }
        }*/
   /*         switch (messageText) {
                case "/start":

                        registerUser(update.getMessage());
                        startCommandReceived(chatId, update.getMessage().getChat().getUserName());
                        break;

                case "/help":

                        sendMessage(chatId, HELP_TEXT);
                        break;

                case "/mydata":

                 //       myDataCommand(chatId, update.getMessage().getChat().getUserName()); //TODO solve data type problem
                        break;



                case "/stop":

                    sendMessage(chatId, "there should be stopping using this bot and receiving news");
                        break;


                case "/register":
                    
                    register(chatId);
                    break;
                    
                default:
                    sendMessage(chatId, "Sorry, command was not recognized.");

            }


            }
        else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals(YES_BUTTON)){
                String text = "You pressed YES button.";
                executeMessageText(chatId, text, messageId);


            } else if (callbackData.equals(NO_BUTTON)) {
                String text = "You pressed NO button.";
                executeMessageText(chatId, text, messageId);
            }
        }*/



    }



    private void register(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Do you really want to register?");

        InlineKeyboardService inlineKeyboardService = new InlineKeyboardService();
        inlineKeyboardService.registerInlineKeyboard();

        executeMessage(message);
    }
    public void executeMessage(SendMessage message){
        try {
            execute(message);
        }
        catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

    private void registerUser(Message msg) {
        if(userRepository.findById(msg.getChatId()).isEmpty()){

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


    private void myDataCommand(long chatId, Message msg) {

      //  User chatId = User.getChatId();
        if(userRepository.findById(msg.getChatId()).isEmpty()){
            String  answer = "User is undefined. For registration choose 'start' command, please.";
            sendMessage(chatId, answer);
        }
        else {
            Optional<User> findChatId = userRepository.findById(chatId);
            sendMessage(chatId, String.valueOf(msg.getChatId()) );  //TODO user data should be here

        }
    }
//TODO create method user data from msg.methods and chat.get... methods


    public String cityUserAnswer(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            prepareAndSendMessage(chatId, "Thank you!");

            return update.getMessage().getText();
        }

     else  {  return null;}
    }

    public void cityAsk(long chatId){
        String text = "Please, write the name of the city.";
        prepareAndSendMessage(chatId,text);
    }


    private void sendToAll(String messageText){
        var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
        var users = userRepository.findAll();
        for (User user: users){
            prepareAndSendMessage(user.getChatId(), textToSend);
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

              //  joke.ifPresent(randomJoke -> sendMessage(chatId, ));
              //  String callbackData = update.getCallbackQuery().getData();
//long chatId = update.getCallbackQuery().getMessage().getChatId();
              currentWeatherCommand(chatId, update);

                break;

            case "/dailyweather":

                //callbackData = update.getCallbackQuery().getData();
                //chatId1 = update.getCallbackQuery().getMessage().getChatId();
               dailyWeatherCommand(chatId, update);

                break;

           /* default:

                prepareAndSendMessage(chatId, "Sorry, command was not recognized");*/

        }


    }


    private void currentWeatherCommand(long chatId, Update update) throws Exception {
        WeatherService weather = new WeatherService();

        cityAsk(chatId);
        String userAnswer = cityUserAnswer(update);
        if(userAnswer == null || userAnswer.isEmpty()){
            cityAsk(chatId);
        }
        else {
            Location location = weather.getLocationObject(userAnswer);
            String locationKey = WeatherService.getLocationKeyString(location);
            weather.sendCurrentWeather(chatId, locationKey);
        }


    }

    private void dailyWeatherCommand(long chatId, Update update) throws Exception {
        WeatherService weather = new WeatherService();
        cityAsk(chatId);
        sleep(50000);


         String userAnswer =  handleUpdate(update);
         prepareAndSendMessage(chatId, userAnswer);
       // String userAnswer = getMessageText();
        userAnswer = cityUserAnswer(update);
        prepareAndSendMessage(chatId, userAnswer);
        userAnswer = getUserMessage(update);
        prepareAndSendMessage(chatId, userAnswer);
        if(userAnswer == null || userAnswer.isEmpty()){
            cityAsk(chatId);
        }
        else {
            Location location = weather.getLocationObject(userAnswer);
            String locationKey = WeatherService.getLocationKeyString(location);
            weather.sendDailyWeather(chatId, locationKey);
        }

    }



    private String getUserMessage(Update update) throws TelegramApiException {

        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Your city: " + text);
        this.execute(sendMessage);
        return text;
    }

    public void startCommandReceived(long chatId, String name){

        String answer = EmojiParser.parseToUnicode("Hi, " + name + ", nice to meet you!" + " :blush:");
        log.info("Replied to user " + name);
        sendMessage(chatId, answer);
    }

    public void sendMessage(long chatId, String textToSend) {
        KeyboardButtonService keyboardButtonService = new KeyboardButtonService();

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        //InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        //var weatherNowButton = new KeyboardButton;

        // weatherNowButton.setText("weather now");
//weatherNowButton.setCallbackData(WEATHER_NOW);

        row.add(WEATHER_NOW);
        row.add("weather forecast for 1 day");

        keyboardRows.add(row);

        row = new KeyboardRow();


        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);

        // message.setReplyMarkup(keyboardMarkup);
        message.setReplyMarkup(keyboardMarkup);
        // message.setReplyMarkup(keyboardMarkup);

        executeMessage(message);
    }

    public SendMessage createMessage( String text) {
        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("markdown");
        Long chatId = getCurrentChatId();
        message.setChatId(chatId);
        return message;
    }

    public Long getCurrentChatId() {
        if (updateEvent.get().hasMessage()) {
            return updateEvent.get().getMessage().getFrom().getId();
        }

        if (updateEvent.get().hasCallbackQuery()) {
            return updateEvent.get().getCallbackQuery().getFrom().getId();
        }

        return null;
    }

    public String getMessageText() {
        return updateEvent.get().hasMessage() ? updateEvent.get().getMessage().getText() : "";
    }

    private void stopChat(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Thank you for your interest to this bot. See you soon!\nPress /start to order again");
        //chatStates.remove(chatId);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        //   sender.execute(sendMessage);
    }


    public String handleUpdate(Update update) throws IOException, InterruptedException {
        String messageText;
        Long chatId;
        String userFirstName = "";

        //если сообщение пришло в лс боту
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            messageText = update.getMessage().getText().toUpperCase(Locale.ROOT).replace("/", "");
            userFirstName = update.getMessage().getChat().getFirstName();
            return messageText;
        }



        return "no city";
    }


    private void executeEditMessageText(long chatId,String text, long messageId){
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);
        try {
            execute(message);
        }
        catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }





    public void prepareAndSendMessage(Long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }




}

