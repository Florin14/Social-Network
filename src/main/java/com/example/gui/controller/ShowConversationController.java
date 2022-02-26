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

public class ShowConversationController implements EventHandler<ActionEvent> {
    Service service;
    FriendshipDto friendshipDto;
    ObservableList<MessageDto> modelGrade = FXCollections.observableArrayList();


    @FXML
    private TableColumn<MessageDto, String> colFriendMessage;

    @FXML
    private TableColumn<MessageDto, LocalDateTime> colFriendMessageDate;

    @FXML
    private TableColumn<MessageDto, String> colMyMessage;

    @FXML
    private TableColumn<MessageDto, LocalDateTime> colMyMessageDate;

    @FXML
    private TableView<MessageDto> tableView;

    @FXML
    private Button sendMessageButton;

    @FXML
    private Button removeMessageButton;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private Label nameLabel;

    @FXML
    private Button cancelButton;

    public void setService(Service service, FriendshipDto friendshipDto) {
        this.friendshipDto = friendshipDto;
        this.service = service;
        modelGrade.setAll(getConversation());
        initialize();
    }

    @FXML
    public void setLabelText(String firstName, String lastName) {
        nameLabel.setText(firstName + " " + lastName + ":");
    }

    public void initialize() {
        colFriendMessage.setCellValueFactory(new PropertyValueFactory<>("friendMessage"));
        colFriendMessageDate.setCellValueFactory(new PropertyValueFactory<>("friendDate"));
        colMyMessage.setCellValueFactory(new PropertyValueFactory<>("currentUserMessage"));
        colMyMessageDate.setCellValueFactory(new PropertyValueFactory<>("currentUserDate"));

        tableView.setItems(modelGrade);
    }

    @Override
    public void handle(ActionEvent event) {
        Long id = 0L;
        if (event.getSource() == cancelButton) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }

        for (User user : service.getAllUsers()) {
            if (user.getFirstName().equals(friendshipDto.getFirstName()) && user.getLastName().equals(friendshipDto.getLastName())) {
                id = user.getId();
            }
        }
        if (event.getSource() == sendMessageButton) {
            if (!messageTextArea.getText().equals(" ")) {
                service.sendMessage(service.getCurrentUserId(), id, messageTextArea.getText());
                messageTextArea.clear();
            } else {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Message", "Please write something!");
            }
        } else if (event.getSource() == removeMessageButton) {
            MessageDto message = tableView.getSelectionModel().getSelectedItem();
            if (message.getFriendMessage().equals(" ") && message.getFriendDate() == null) {
                for (Message message1 : service.showConversation(service.getCurrentUserId(), id)) {
                    if (message1.getMessage().equals(message.getCurrentUserMessage()) && message1.getDate().equals(message.getCurrentUserDate())) {
                        service.deleteMessage(message1.getId());
                    }
                }
            }
        }
        modelGrade.setAll(getConversation());
        initialize();
    }

    public ObservableList<MessageDto> getConversation() {
        Long id = 0L;
        ObservableList<MessageDto> messages = FXCollections.observableArrayList();
        for (User user : service.getAllUsers()) {
            if (user.getFirstName().equals(friendshipDto.getFirstName()) && user.getLastName().equals(friendshipDto.getLastName())) {
                id = user.getId();

            }
        }

        for (Message message : service.showConversation(id, service.getCurrentUserId())) {
            if (message.getFrom().equals(service.getCurrentUserId())) {
                messages.add(new MessageDto(" ", null, message.getMessage(), message.getDate()));
            } else {
                messages.add(new MessageDto(message.getMessage(), message.getDate(), " ", null));
            }
        }
        return messages;
    }

}
