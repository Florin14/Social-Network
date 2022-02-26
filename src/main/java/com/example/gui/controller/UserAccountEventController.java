package com.example.gui.controller;

import com.example.gui.domain.Event;
import com.example.gui.domain.User;
import com.example.gui.service.Service;
import com.example.gui.utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserAccountEventController implements EventHandler<ActionEvent> {
    private Service service;

    private final SceneUtils sceneUtils = new SceneUtils();

    private int currentPage = 0;

    @FXML
    private Button addEventButton;

    @FXML
    private Button removeEventButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button conversationButton;

    @FXML
    private Button discoverButton;

    @FXML
    private Label dateLabel;

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Button eventsButton;

    @FXML
    private Button exploreButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label peopleRespondedLabel;

    @FXML
    private Label placeLabel;

    @FXML
    private Button profileButton;

    @FXML
    private Button yourEventsButton;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label welcomeLabel;

    public void setService(Service service) {
        this.service = service;
        initialize();
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
        } else if (event.getSource() == addEventButton) {
            try {
                sceneUtils.switchToAddEventScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == removeEventButton) {
            Iterable<Event> events = service.printAllEvents();
            List<Event> allEvents = new ArrayList<>();
            for (Event event1 : events) {
                if (event1.getCreator().equals(service.getCurrentUserId())) {
                    allEvents.add(event1);
                }
            }
            if (allEvents.size() != 0) {
                service.deleteEvent(allEvents.get(currentPage).getId());
                //currentPage--;
            } else {
                eventNameLabel.setText(" ");
                peopleRespondedLabel.setText(" ");
                placeLabel.setText(" ");
                categoryLabel.setText(" ");
                descriptionLabel.setText(" ");
                dateLabel.setText(" ");

            }
            initialize();

        } else if (event.getSource() == yourEventsButton) {
            try {
                sceneUtils.switchToUserEventScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == discoverButton) {
            try {
                sceneUtils.switchToDiscoverEventsScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == previousButton) {
            if (currentPage > 0) {
                currentPage--;
                initialize();
            }
        } else if (event.getSource() == nextButton) {
            Iterable<Event> events = service.printAllEvents();
            List<Event> allEvents = new ArrayList<>();
            for (Event event1 : events) {
                if (event1.getCreator().equals(service.getCurrentUserId())) {
                    allEvents.add(event1);
                }
            }
            if (allEvents.size() > currentPage + 1) {
                currentPage++;
                initialize();
            }
        }
    }

    private void initialize() {
        Iterable<Event> events = service.printAllEvents();
        List<Event> allEvents = new ArrayList<>();
        for (Event event1 : events) {
            if (event1.getCreator().equals(service.getCurrentUserId())) {
                allEvents.add(event1);
            }
        }
        if (allEvents.size() != 0) {
            eventNameLabel.setText(allEvents.get(currentPage).getName());
            peopleRespondedLabel.setText(String.valueOf(service.nrOfParticipants(allEvents.get(currentPage))));
            placeLabel.setText(allEvents.get(currentPage).getPlace());
            categoryLabel.setText(String.valueOf(allEvents.get(currentPage).getType()));
            descriptionLabel.setText(allEvents.get(currentPage).getDescription());
            dateLabel.setText(String.valueOf(allEvents.get(currentPage).getDate()));
        }
    }

    public void setLabelText(User user) {
        welcomeLabel.setText(user.getLastName() + " " + user.getFirstName() + "\nFriends:" + service.getNrOfFriends(user.getId()));
    }

}
