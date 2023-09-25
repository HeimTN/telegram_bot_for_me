package com.heimtn.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Service for processing incoming messages from users.
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private static final String respElephant = "А ты купи слона!";

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * A method for tracking changes in chat with users.
     * @param updates List of changes that are currently taking place.
     * @return Once the change sheet has been processed, return that all is well
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Message message = update.message();
            //Process the message if it has arrived
            if (message != null) {
                String userMessage = message.text();
                long chatId = message.chat().id();

                //If the message is "/start" send a greeting message
                if(userMessage.equals("/start")){
                    sendMessage(chatId, "Купи слона!");
                }
                //If the message is different, send a universal reply.
                else {
                    String response = "Все говорят: " + userMessage;
                    sendMessage(chatId, response);
                    sendMessage(chatId, respElephant);
                }

            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * A small method for generating and sending messages to the user on behalf of the bot
     * @param chatId chat id, found out via message.chat().id();
     * @param text What kind of message we want to send
     */
    private void sendMessage(long chatId, String text){
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }

}
