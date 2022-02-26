package com.example.gui.controller;

import com.example.gui.domain.validators.ValidationException;
import com.example.gui.service.Service;
import com.example.gui.utils.Encryptor;
import com.example.gui.utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class SignUpController implements EventHandler<ActionEvent> {
    private Service service;
    private final SceneUtils sceneUtils = new SceneUtils();

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    private Button signUpButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button signInButton;

    Encryptor encryptor = new Encryptor();

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == cancelButton){
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            service.logoutUser();
        }else if(event.getSource() == signUpButton){
            try {
                handleSave(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(event.getSource() == signInButton){
            try {
                sceneUtils.switchToLoginScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleSave(ActionEvent event) throws IOException {
        String firstName = firstnameTextField.getText();
        String lastName = lastnameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordPasswordField.getText();
        try {
            if (service.createUser(firstName, lastName, email, encryptor.encryptString(password))) {
                try {
                    sceneUtils.switchToUserProfileScene(event, service);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            } else {
                firstnameTextField.clear();
                lastnameTextField.clear();
                emailTextField.clear();
                passwordPasswordField.clear();
            }
        }catch(ValidationException | NullPointerException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }
}
