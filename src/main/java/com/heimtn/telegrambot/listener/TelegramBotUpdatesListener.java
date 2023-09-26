package com.heimtn.telegrambot.listener;

import com.heimtn.telegrambot.entity.NotificationTask;
import com.heimtn.telegrambot.exceptions.TimeParseException;
import com.heimtn.telegrambot.service.NotificationTaskService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service for processing incoming messages from users.
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private NotificationTaskService taskService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Every minute we check to see if there are any notifications that need to be sent.
     * All found shuffles are sent to the right place at once.
     */
    @Scheduled(cron= "0 0/1 * * * *")
    public void findTask(){
        Collection<NotificationTask> tasks = taskService.getTaskByTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        if(!tasks.isEmpty()){
            tasks.forEach(task -> {
                sendMessage(task.getChatId(), task.getText());
            });
        }
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
                //If the message matches the format "01.01.0001 01:01 msg" then parsing of this message is enabled.
                else if(userMessage.matches("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2} .+")){
                    saveTask(chatId, userMessage);
                    sendMessage(chatId, "Задача сохранена! Между делом купи слона :)");
                }
                //If the message is different, send a universal reply.
                else {
                    String response = "Все говорят: " + userMessage;
                    sendMessage(chatId, response);
                    sendMessage(chatId, "А ты купи слона!");
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
        logger.info("Send message: {}", message);
        telegramBot.execute(message);
    }

    /**
     * Parses the message, extracts the date and the message itself.
     * @param message
     * @return Returning an unfinished task
     */
    private NotificationTask parsingDate(String message){
        Pattern pattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}) .+");
        Matcher matcher = pattern.matcher(message);
        if(matcher.find()){
            String timeString = matcher.group(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm");
            NotificationTask task = new NotificationTask();
            task.setTime(LocalDateTime.parse(timeString, formatter));
            task.setText(matcher.group(2));
            return task;
        } else {
            logger.error("Parse date error: message:{}", message);
            throw new TimeParseException("Problem with message, check parseDate method in TelegramBotUpdatesListener class");
        }
    }

    /**
     * Complete the task and save it in the database
     * @param chatId chat id, found out via message.chat().id();
     * @param text message with the date to be parsed
     */
    private void saveTask(long chatId, String text){
        NotificationTask task = parsingDate(text);
        task.setChatId(chatId);
        logger.info("Save task: {}", task);
        taskService.addTask(task);
    }

}
