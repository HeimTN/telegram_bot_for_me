package com.heimtn.telegrambot.repositories;

import com.heimtn.telegrambot.entity.NotificationTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Repository for NotificationTask.
 */
public interface NotificationTaskRepositories extends JpaRepository<NotificationTask, Long> {
    Collection<NotificationTask> findByTimeMsg(LocalDateTime time);
}
