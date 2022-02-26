package com.example.gui.controller;

import com.example.gui.service.Service;
import com.example.gui.utils.Encryptor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.security.NoSuchAlgorithmException;

public class EditPasswordController implements EventHandler<ActionEvent> {
    private Service service;
    Encryptor encryptor = new Encryptor();
    @FXML
    private Button cancelButton;

    @FXML
    private PasswordField confirmPasswordPasswordField;

    @FXML
    private Button modifyButton;

    @FXML
    private PasswordField newPasswordPasswordField;

    @FXML
    private PasswordField oldPasswordPasswordField;

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == cancelButton) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } else if (event.getSource() == modifyButton) {
            try {
                if (encryptor.encryptString(oldPasswordPasswordField.getText()).equals(service.getCurrentUser().getPasswordHash()) && confirmPasswordPasswordField.getText().equals(newPasswordPasswordField.getText())) {
                    this.service.updateUser(encryptor.encryptString(newPasswordPasswordField.getText()), "", "");
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Password", "Password modified!");
                    Stage stage = (Stage) modifyButton.getScene().getWindow();
                    stage.close();
                } else if (!encryptor.encryptString(oldPasswordPasswordField.getText()).equals(service.getCurrentUser().getPasswordHash())) {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Password", "The old password is incorrect!");
                    oldPasswordPasswordField.clear();
                } else if (!confirmPasswordPasswordField.getText().equals(newPasswordPasswordField.getText())) {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Password", "New and confirm password do not match!");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}
