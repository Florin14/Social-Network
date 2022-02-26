package com.example.gui.domain;

public class Participant extends Entity<Long> {
    private Long event;
    private Long participant;


    public Participant(Long event, Long participant ) {
        this.event = event;
        this.participant = participant;
    }

    public Long getEvent() {
        return event;
    }

    public void setEvent(Long event) {
        this.event = event;
    }

    public Long getParticipant() {
        return participant;
    }

    public void setParticipant(Long participant) {
        this.participant = participant;
    }
}
