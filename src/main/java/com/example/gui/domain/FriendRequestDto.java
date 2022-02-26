package com.example.gui.domain;

import java.time.LocalDateTime;

public class FriendRequestDto {
    private String firstName,lastName,status;
    private LocalDateTime date;

    public FriendRequestDto(String firstName, String lastName, String status, LocalDateTime date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.date = date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
