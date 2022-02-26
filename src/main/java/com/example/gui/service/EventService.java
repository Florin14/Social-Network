package com.example.gui.service;

import com.example.gui.domain.Event;
import com.example.gui.domain.Participant;
import com.example.gui.domain.validators.ValidationException;
import com.example.gui.repository.Repository;

public class EventService {
    private final Repository<Long, Event> eventRepository;

    public EventService(Repository<Long, Event> eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void saveEvent(Event event) {
        try {
            this.eventRepository.save(event);
        } catch (ValidationException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void deleteEvent(Long id) {
        try {
            this.eventRepository.delete(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Event findOne(Long x) {
        try {
            return this.eventRepository.findOne(x);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Iterable<Event> findAll() {
        try {
            return this.eventRepository.findAll();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

}
