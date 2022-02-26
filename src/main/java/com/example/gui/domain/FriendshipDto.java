package com.example.gui.domain;

import java.time.LocalDateTime;

public class FriendshipDto {
    private String firstName;
    private String lastName;
    private LocalDateTime friendshipDate;

    public FriendshipDto(String firstName, String lastName, LocalDateTime friendshipDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.friendshipDate = friendshipDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDateTime getFriendshipDate() {
        return friendshipDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFriendshipDate(LocalDateTime friendshipDate) {
        this.friendshipDate = friendshipDate;
    }

    @Override
    public String toString() {
        return "FriendshipDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", friendshipDate=" + friendshipDate +
                '}';
    }
}
