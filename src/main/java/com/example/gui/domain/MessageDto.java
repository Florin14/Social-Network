package com.example.gui.domain;

import java.time.LocalDateTime;

public class MessageDto {
    private String friendMessage;
    private LocalDateTime friendDate;
    private String currentUserMessage;
    private LocalDateTime currentUserDate;

    public MessageDto(String friendMessage, LocalDateTime friendDate, String curentUserMessage, LocalDateTime curentUserDate) {
        this.friendMessage = friendMessage;
        this.friendDate = friendDate;
        this.currentUserMessage = curentUserMessage;
        this.currentUserDate = curentUserDate;
    }

    public String getFriendMessage() {
        return friendMessage;
    }

    public void setFriendMessage(String friendMessage) {
        this.friendMessage = friendMessage;
    }

    public LocalDateTime getFriendDate() {
        return friendDate;
    }

    public void setFriendDate(LocalDateTime friendDate) {
        this.friendDate = friendDate;
    }

    public String getCurrentUserMessage() {
        return currentUserMessage;
    }

    public void setCurrentUserMessage(String curentUserMessage) {
        this.currentUserMessage = curentUserMessage;
    }

    public LocalDateTime getCurrentUserDate() {
        return currentUserDate;
    }

    public void setCurrentUserDate(LocalDateTime curentUserDate) {
        this.currentUserDate = curentUserDate;
    }
}
