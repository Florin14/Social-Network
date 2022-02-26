package com.example.gui.domain.validators;

import com.example.gui.controller.MessageAlert;
import com.example.gui.domain.Event;
import javafx.scene.control.Alert;

import java.time.LocalDate;

public class EventValidator implements Validator<Event> {
    public EventValidator() {
    }

    @Override
    public void validate(Event entity) throws ValidationException {
        String message = "";

        if (entity.getDescription().length() == 0) {
            message += "Description can't be an empty string!\n";
        }
        if (entity.getPlace().length() == 0) {
            message += "Place can't be an empty string!\n";
        }
        if (entity.getName().length() == 0) {
            message += "Name can't be an empty string!\n";
        }
        if(!entity.getDate().isAfter(LocalDate.now())){
            message += "Please select a future date for your event!";
        }

        if (message.length() > 0) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Sign up", message);
            throw new ValidationException(message);
        }
    }
}
