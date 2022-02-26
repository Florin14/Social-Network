package com.example.gui.domain.validators;

import com.example.gui.controller.MessageAlert;
import com.example.gui.domain.User;
import javafx.scene.control.Alert;

public class UserValidator implements Validator<User> {

    public UserValidator() {
    }

    @Override
    public void validate(User entity) throws ValidationException {
        String message = "";
        if (entity.getFirstName().length() == 0) {
            message += "First name can't be an empty string!\n";
        }
        if (entity.getLastName().length() == 0) {
            message += "Last name can't be an empty string!\n";
        }
        if (entity.getPasswordHash().length() == 0) {
            message += "Password can't be an empty string!\n";
        }
        if (!entity.getEmail().endsWith("@gmail.com") && !entity.getEmail().endsWith("@yahoo.com") && !entity.getEmail().endsWith("@outlook.com") && !entity.getEmail().endsWith("@facebook.com")) {
            message += "Please enter a valid email!\n";
        }
        if (message.length() > 0) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Sign up", message);
            throw new ValidationException(message);

        }
    }
}
