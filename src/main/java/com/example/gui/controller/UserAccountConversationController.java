package com.example.gui.controller;

import com.example.gui.domain.Friendship;
import com.example.gui.domain.FriendshipDto;
import com.example.gui.domain.User;
import com.example.gui.service.Service;
import com.example.gui.utils.SceneUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserAccountConversationController implements EventHandler<ActionEvent> {
    private Service service;
    private final SceneUtils sceneUtils = new SceneUtils();
    ObservableList<FriendshipDto> modelGrade = FXCollections.observableArrayList();

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableView<FriendshipDto> tableView;

    @FXML
    private TableColumn<FriendshipDto, String> colFirstName;

    @FXML
    private TableColumn<FriendshipDto, String> colLastName;

    @FXML
    private TableColumn<FriendshipDto, LocalDateTime> colFriendshipDate;

    @FXML
    private TextField searchFriendTextField;

    @FXML
    private Button showConversationButton;

    @FXML
    private Button removeFriendButton;

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

    public void setService(Service service1) {
        this.service = service1;
        modelGrade.setAll(getFriends());
        initialize();
    }

    @Override
    public void handle(ActionEvent event) {
        FriendshipDto friendshipDTO = tableView.getSelectionModel().getSelectedItem();
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
        } else if (event.getSource() == showConversationButton && friendshipDTO != null) {
            try {
                sceneUtils.switchToShowConversationScene(this.service, friendshipDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == removeFriendButton && friendshipDTO != null) {
            for (Friendship friendship : service.getUserFriendships()) {
                if (friendship.getId1().equals(service.getCurrentUserId())) {
                    if (friendshipDTO.getFirstName().equals(service.findOne(friendship.getId2()).getFirstName()) && friendshipDTO.getLastName().equals(service.findOne(friendship.getId2()).getLastName())) {
                        service.removeFriend(friendship.getId());
                        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Your friend was successfully removed!");
                    }
                } else if (friendship.getId2().equals(service.getCurrentUserId())) {
                    if (friendshipDTO.getFirstName().equals(service.findOne(friendship.getId1()).getFirstName()) && friendshipDTO.getLastName().equals(service.findOne(friendship.getId1()).getLastName())) {
                        service.removeFriend(friendship.getId());
                        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Your friend was successfully removed!");
                    }
                }
            }
        } else {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "There are no selected friends!");
        }
        modelGrade.setAll(getFriends());
        setLabelText(service.getCurrentUser());
        initialize();
    }

    public void initialize() {
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colFriendshipDate.setCellValueFactory(new PropertyValueFactory<>("friendshipDate"));

        tableView.setItems(modelGrade);

        searchFriendTextField.textProperty().addListener(o -> handleFilter());
    }

    private void handleFilter() {
        Predicate<FriendshipDto> p1 = n -> n.getFirstName().toLowerCase().startsWith(searchFriendTextField.getText().toLowerCase());
        Predicate<FriendshipDto> p2 = n -> n.getLastName().toLowerCase().startsWith(searchFriendTextField.getText().toLowerCase());

        modelGrade.setAll(getFriends()
                .stream()
                .filter(p1.or(p2))
                .collect(Collectors.toList()));
    }

    public ObservableList<FriendshipDto> getFriends() {
        ObservableList<FriendshipDto> friends = FXCollections.observableArrayList();
        for (Friendship friendship : service.getAllFriendships()) {
            if (friendship.getId1().equals(service.getCurrentUserId())) {
                User user = service.findOne(friendship.getId2());
                friends.add(new FriendshipDto(user.getFirstName(), user.getLastName(), friendship.getFriendshipDate()));
            } else if (friendship.getId2().equals(service.getCurrentUserId())) {
                User user = service.findOne(friendship.getId1());
                friends.add(new FriendshipDto(user.getFirstName(), user.getLastName(), friendship.getFriendshipDate()));
            }
        }
        return friends;
    }

    public void setLabelText(User user) {
        welcomeLabel.setText(user.getLastName() + " " + user.getFirstName() + "\nFriends:" + service.getNrOfFriends(user.getId()));
    }
}
