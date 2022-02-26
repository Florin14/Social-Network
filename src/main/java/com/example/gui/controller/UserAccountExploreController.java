package com.example.gui.controller;

import com.example.gui.domain.*;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserAccountExploreController implements EventHandler<ActionEvent> {
    private Service service;

    private final SceneUtils sceneUtils = new SceneUtils();

    ObservableList<Username> modelGrade = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Username, String> colFirstname;

    @FXML
    private TableColumn<Username, String> colLastname;

    @FXML
    private TableView<Username> tableView;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button conversationButton;

    @FXML
    private Button eventsButton;

    @FXML
    private Button exploreButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button friendRequestsButton;

    @FXML
    private Button myFriendRequestsButton;

    public void setService(Service service1) {
        this.service = service1;
        modelGrade.setAll(allUsers());
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
        } else if (event.getSource() == eventsButton) {
            try {
                sceneUtils.switchToUserEventScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == profileButton) {
            try {
                sceneUtils.switchToUserProfileScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == friendRequestsButton) {
            try {
                initialize();
                sceneUtils.showFriendRequestsDialog( this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == myFriendRequestsButton) {
            try {
                initialize();
                sceneUtils.showMyFriendRequestsDialog( this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == exploreButton) {
            try {
                sceneUtils.switchToUserExploreScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize() {
        colFirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        tableView.setItems(modelGrade);

        searchTextField.textProperty().addListener(o -> handleFilter());
    }

    private void handleFilter() {
        Predicate<Username> p1 = n -> n.getFirstName().toLowerCase().startsWith(searchTextField.getText().toLowerCase());
        Predicate<Username> p2 = n -> n.getLastName().toLowerCase().startsWith(searchTextField.getText().toLowerCase());

        modelGrade.setAll(allUsers()
                .stream()
                .filter(p1.or(p2))
                .collect(Collectors.toList()));
    }

    public ObservableList<Username> allUsers() {
        ObservableList<Username> users = FXCollections.observableArrayList();
        for (User user : service.getAllUsers()) {
            boolean flag = false;
            if (!service.ifFriend(user.getId())) {
                for (FriendRequest friendRequest : service.printMyFriendRequests()) {
                    User user1 = service.findOne(friendRequest.getId2());
                    if (user.equals(user1)) {
                        flag = true;
                    }
                }
                if (!flag) {
                    users.add(new Username(user.getFirstName(), user.getLastName()));
                }
            }
        }
        return users;
    }

    public void handleSendFriendRequest() {
        Username username = tableView.getSelectionModel().getSelectedItem();
        if (username != null) {
            for (User user : service.getAllUsers()) {
                if (user.getFirstName().equals(username.getFirstName()) && user.getLastName().equals(username.getLastName())) {
                    service.sendFriendRequest(user.getId());
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Friend request", "The friend request was sent!");
                }
            }
        } else {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Friend request", "There are no users selected!");
        }
        modelGrade.setAll(allUsers());
        initialize();
    }

    public void setLabelText(User user) {
        welcomeLabel.setText(user.getLastName() + " " + user.getFirstName() + "\nFriends:" + service.getNrOfFriends(user.getId()));
    }

}
