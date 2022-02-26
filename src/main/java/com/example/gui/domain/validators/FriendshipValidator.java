package com.example.gui.domain.validators;

import com.example.gui.domain.Friendship;

public class FriendshipValidator implements Validator<Friendship> {

    public FriendshipValidator() {
    }

    @Override
    public void validate(Friendship entity) throws ValidationException {
        String message = "";

        if (entity.getId1().equals(entity.getId2())) {
            message += "User id's can't be equal!";
        }
        if (entity.getId1() <= 0L || entity.getId2() <= 0L) {
            message += "User id's can't be negative!";
        }

        if (message.length() > 0) {
            throw new ValidationException(message);
        }
    }
}