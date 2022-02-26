package com.example.gui.controller;

import com.example.gui.service.Service;
import com.example.gui.utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ConfirmationController implements EventHandler<ActionEvent> {
    private Service service;
    private final SceneUtils sceneUtils = new SceneUtils();

    @FXML
    private Button noButton;

    @FXML
    private Button yesButton;

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == yesButton) {
            service.deleteAccount();
            try {
                sceneUtils.switchToLoginScene(event, service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == noButton) {
            try {
                sceneUtils.switchToUserProfileScene(event, service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
