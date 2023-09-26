package com.heimtn.telegrambot.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * To the created table via liquibase, make a class with Entity annotation.
 */
@Entity
public class NotificationTask {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "text_msg")
    private String text;
    @Column(name="time_msg")
    private LocalDateTime time;

    /**
     * Define getters and setters, as well as methods equals, hashcode and toString
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, text, time);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }
}
