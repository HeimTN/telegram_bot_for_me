package com.heimtn.telegrambot.repositories;

import com.heimtn.telegrambot.entity.NotificationTask;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for NotificationTask.
 */
public interface NotificationTaskRepositories extends JpaRepository<NotificationTask, Long> {
}
