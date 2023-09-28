package com.heimtn.telegrambot.repositories;

import com.heimtn.telegrambot.entity.NotificationTask;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Repository for NotificationTask.
 */
@Table(schema = "notification_task")
public interface NotificationTaskRepositories extends JpaRepository<NotificationTask, Long> {
    Collection<NotificationTask> findByTime(LocalDateTime time);
}
