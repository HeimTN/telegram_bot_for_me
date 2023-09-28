package com.heimtn.telegrambot.service;

import com.heimtn.telegrambot.entity.NotificationTask;
import com.heimtn.telegrambot.repositories.NotificationTaskRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Service for working with the repository
 */
@Service
public class NotificationTaskService {

    private final NotificationTaskRepositories taskRepositories;

    public NotificationTaskService(NotificationTaskRepositories taskRepositories){
        this.taskRepositories = taskRepositories;
    }

    public NotificationTask addTask(NotificationTask task){
        return taskRepositories.save(task);
    }

    public NotificationTask getTask(long id){
        return taskRepositories.findById(id).orElse(null);
    }


    public void deleteTask(long id){
        taskRepositories.deleteById(id);
    }

    public Collection<NotificationTask> getTaskByTime(LocalDateTime time){
        return taskRepositories.findByTime(time);
    }
}
