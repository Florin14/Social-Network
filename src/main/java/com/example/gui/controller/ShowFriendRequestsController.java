package com.example.gui.controller;

import com.example.gui.domain.*;
import com.example.gui.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class ShowFriendRequestsController implements EventHandler<ActionEvent> {
    private Service service;

    ObservableList<FriendRequestDto> modelGrade = FXCollections.observableArrayList();

    @FXML
    private TableColumn<FriendRequestDto, String> colFirstName;

    @FXML
    private TableColumn<FriendRequestDto, String> colLastName;

    @FXML
    private TableColumn<FriendRequestDto, String> colStatus;

    @FXML
    private TableColumn<FriendRequestDto, LocalDateTime> colDate;

    @FXML
    private TableView<FriendRequestDto> tableView;

    @FXML
    private Button removeButton;

    @FXML
    private Button cancelButton;

    public void setService(Service service1) {
        this.service = service1;
        modelGrade.setAll(getFriendRequests());
        initialize();
    }

    public void initialize() {
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableView.setItems(modelGrade);
    }

    @Override
    public void handle(ActionEvent event) {
        Long id = 0L;
        if (event.getSource() == cancelButton) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } else if (event.getSource() == removeButton) {
            FriendRequestDto friendRequestDTO = tableView.getSelectionModel().getSelectedItem();
            if (friendRequestDTO != null) {
                for (FriendRequest friendRequest : service.printMyFriendRequests()) {
                    if (friendRequest.getId1().equals(service.getCurrentUserId())) {
                        User user = service.findOne(friendRequest.getId2());
                        if (user.getLastName().equals(friendRequestDTO.getLastName()) && user.getFirstName().equals(friendRequestDTO.getFirstName())) {
                            id = friendRequest.getId();
                        }
                    }
                }
                for (User user : service.getAllUsers()) {
                    if (user.getFirstName().equals(friendRequestDTO.getFirstName()) && user.getLastName().equals(friendRequestDTO.getLastName())) {
                        service.deleteFriendRequests(id);
                        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Friend Request", "Friend request removed!");
                    }
                }
            } else {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Friend Request", "There are no selected friend requests!");
            }
        }
        modelGrade.setAll(getFriendRequests());
        initialize();
    }

    public ObservableList<FriendRequestDto> getFriendRequests() {
        ObservableList<FriendRequestDto> friendRequests = FXCollections.observableArrayList();
        for (FriendRequest friendRequest : service.printMyFriendRequests()) {
            if (friendRequest.getId1().equals(service.getCurrentUserId())) {
                User user = service.findOne(friendRequest.getId2());
                friendRequests.add(new FriendRequestDto(user.getFirstName(), user.getLastName(), friendRequest.getStatus(), friendRequest.getDate()));
            }
        }
        return friendRequests;
    }
}

