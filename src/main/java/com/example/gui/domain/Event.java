package com.example.gui.domain;

import java.time.LocalDate;

public class Event extends Entity<Long> {
    private Long creator;
    private String name;
    private String place;
    private String type;
    private String description;
    private LocalDate date;

    public Event(Long creator, String name, String place, String type, String description, LocalDate date) {
        this.creator = creator;
        this.name = name;
        this.place = place;
        this.type = type;
        this.description = description;
        this.date = date;

    }

    public Long getCreator() {
        return creator;
    }

    public String getName() {
        return this.name;
    }

    public String getPlace() {
        return this.place;
    }

    public String getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateTime(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id = '" + id + '\'' +
                "creator = '" + creator + '\'' +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event that)) return false;
        return getCreator().equals(that.getCreator()) && getName().equals(that.getName()) && getPlace().equals(that.getPlace()) &&
                getType().equals(that.getType()) && getDescription().equals(that.getDescription())
                && getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

