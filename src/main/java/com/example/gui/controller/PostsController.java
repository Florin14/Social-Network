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


public class PostsController implements EventHandler<ActionEvent> {
    private Service service;
    ObservableList<Text> modelGrade = FXCollections.observableArrayList();


    @FXML
    private Button cancelButton;

    @FXML
    private Button removePostButton;

    @FXML
    private Button savePostButton;

    @FXML
    private TextArea textArea;

    @FXML
    private TableView<Text> tableView;

    @FXML
    private TableColumn<Text, String> colPost;

    public void setService(Service service) {
        this.service = service;
        modelGrade.setAll(getPosts());
        initialize();
    }

    public void initialize() {
        colPost.setCellValueFactory(new PropertyValueFactory<>("text"));

        tableView.setItems(modelGrade);
    }

    @Override
    public void handle(ActionEvent event) {
        Text text = tableView.getSelectionModel().getSelectedItem();
        if (event.getSource() == cancelButton) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } else if (event.getSource() == removePostButton && text != null) {
            for (Post post : service.printAllPosts()) {
                if (post.getCreator().equals(service.getCurrentUserId()) && post.getPost().equals(text.getText()))
                    service.deletePost(post.getId());
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Post", "Post removed!");
            }
        } else if (event.getSource() == savePostButton) {
            service.savePost(textArea.getText());
            textArea.clear();
        } else {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Post", "Please select a post!");
        }
        modelGrade.setAll(getPosts());
        initialize();
    }

    public ObservableList<Text> getPosts() {
        ObservableList<Text> posts = FXCollections.observableArrayList();

        for (Post post : service.printAllPosts()) {
            posts.add(new Text(post.getPost()));
        }
        return posts;
    }

}
