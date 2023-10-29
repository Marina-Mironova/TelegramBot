package com.project.telegrambot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


import java.sql.Timestamp;


@Entity(name = "userDataTable")
public class User {

    @Id
    private Long chatId;


    private String userName;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }

    private Timestamp registeredAt;
}
