package com.heimtn.telegrambot.service;

import com.heimtn.telegrambot.entity.NotificationTask;
import com.heimtn.telegrambot.repositories.NotificationTaskRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for working with the repository
 */
@Service
public class NotificationTaskService {
    @Autowired
    private NotificationTaskRepositories taskRepositories;

    public NotificationTask addTask(NotificationTask task){
        return taskRepositories.save(task);
    }

    public NotificationTask getTask(long id){
        return taskRepositories.findById(id).get();
    }

    public NotificationTask editTask(NotificationTask task){
        return taskRepositories.save(task);
    }

    public void deleteTask(long id){
        taskRepositories.deleteById(id);
    }
}
