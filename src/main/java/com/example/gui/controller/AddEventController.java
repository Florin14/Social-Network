package com.example.gui.controller;

import com.example.gui.domain.User;
import com.example.gui.service.Service;
import com.example.gui.utils.SceneUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AddEventController implements EventHandler<ActionEvent> {
    private Service service;

    private final SceneUtils sceneUtils = new SceneUtils();

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button conversationButton;

    @FXML
    private Button exploreButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button eventsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addEventButton;

    @FXML
    private ComboBox<String> categoryCombobox;

    @FXML
    private Button currentAddButton;

    @FXML
    private DatePicker dateLabel;

    @FXML
    private Button discoverButton;

    @FXML
    private TextField locationLabel;

    @FXML
    private TextField nameLabel;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button yourEventsButton;

    public void setService(Service service) {
        ObservableList<String> categories = FXCollections.observableArrayList("MUSIC", "SPORT", "ART", "FOOD", "COMEDY", "GAMES");
        categoryCombobox.setItems(categories);
        this.service = service;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == cancelButton) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            service.logoutUser();
        } else if (event.getSource() == logoutButton) {
            try {
                sceneUtils.switchToLoginScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == conversationButton) {
            try {
                sceneUtils.switchToUserConversationScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == exploreButton) {
            try {
                sceneUtils.switchToUserExploreScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == profileButton) {
            try {
                sceneUtils.switchToUserProfileScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == eventsButton) {
            try {
                sceneUtils.switchToUserEventScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == yourEventsButton) {
            try {
                sceneUtils.switchToUserEventScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == currentAddButton) {
            try {
                sceneUtils.switchToAddEventScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == discoverButton) {
            try {
                this.sceneUtils.switchToDiscoverEventsScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == addEventButton) {
            handleSave();
        }
    }

    public void handleSave() {
        String eventName = nameLabel.getText();
        String eventLocation = locationLabel.getText();
        LocalDate date = dateLabel.getValue();
        String category = categoryCombobox.getSelectionModel().getSelectedItem();
        String description = descriptionTextArea.getText();

        if (eventName.isBlank() || eventLocation.isBlank() || date.toString().isBlank() || category.isBlank() || description.isBlank() || !date.isAfter(LocalDate.now())) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Add event", "Please fill in all the blanks and set the date correctly!");
        } else {
            service.saveEvent(eventName, eventLocation, category, description, date);
            nameLabel.clear();
            locationLabel.clear();
            descriptionTextArea.clear();
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Add event", "Event added successfully!");
        }
    }

    public void setLabelText(User user) {
        welcomeLabel.setText(user.getLastName() + " " + user.getFirstName() + "\nFriends:" + service.getNrOfFriends(user.getId()));
    }
}
