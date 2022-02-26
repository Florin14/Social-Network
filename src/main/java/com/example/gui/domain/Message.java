package com.example.gui.domain;

import java.time.LocalDateTime;

public class Message extends Entity<Long> {
    private Long from;
    private Long to;
    private String message;
    private LocalDateTime date;
    public Long reply;

    public Message(Long from, Long to, String message, LocalDateTime date, Long reply) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
        this.reply = reply;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long aLong) {
        this.from = aLong;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long aLong1) {
        this.to = aLong1;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getReply() {
        return reply;
    }

    public void setReply(Long reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id = '" + id + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                ", reply='" + reply + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message that)) return false;
        return getFrom().equals(that.getFrom()) && getTo().equals(that.getTo()) &&
                getMessage().equals(that.getMessage());
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }


}



