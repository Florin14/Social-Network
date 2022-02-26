package com.example.gui.utils;

import com.example.gui.Main;
import com.example.gui.controller.*;
import com.example.gui.domain.FriendshipDto;
import com.example.gui.domain.User;
import com.example.gui.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneUtils {
    private Parent root;
    private Stage stage;
    private Service service;

    public SceneUtils() {

    }

    public void switchToLoginScene1(Stage stage, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/login.fxml"));
        BorderPane borderPane = fxmlLoader.load();
        stage.setTitle("Ball");
        Scene scene = new Scene(borderPane, 520, 400);
        stage.setScene(scene);
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);
        stage.show();
    }

    public void switchToLoginScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/login.fxml"));
        Scene scene;
        root = fxmlLoader.getRoot();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 520, 400);
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(this.service);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSignUpScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/signUp.fxml"));
        Scene scene;
        root = fxmlLoader.getRoot();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(fxmlLoader.load(), 520, 400);
        SignUpController signUpController = fxmlLoader.getController();
        signUpController.setService(this.service);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToUserProfileScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/userAccountProfile.fxml"));
        Scene scene;
        root = fxmlLoader.getRoot();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(fxmlLoader.load(), 800, 600);
        User user = service.getCurrentUser();
        user.setId(service.getCurrentUserId());

        UserAccountProfileController userAccountController = fxmlLoader.getController();
        userAccountController.setService(this.service);
        userAccountController.setLabelText(user);

        stage.setScene(scene);
        stage.show();
    }

    public void switchToUserConversationScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/userAccountConversation.fxml"));
        Scene scene;
        root = fxmlLoader.getRoot();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(fxmlLoader.load(), 800, 600);
        User user = service.getCurrentUser();
        user.setId(service.getCurrentUserId());
        UserAccountConversationController userAccountController = fxmlLoader.getController();
        userAccountController.setService(this.service);
        userAccountController.setLabelText(user);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToUserEventScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/userAccountEvent.fxml"));
        Scene scene;
        root = fxmlLoader.getRoot();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(fxmlLoader.load(), 800, 600);
        User user = service.getCurrentUser();
        user.setId(service.getCurrentUserId());
        UserAccountEventController userAccountController = fxmlLoader.getController();
        userAccountController.setService(this.service);
        userAccountController.setLabelText(user);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToUserExploreScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/userAccountExplore.fxml"));
        Scene scene;
        root = fxmlLoader.getRoot();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        User user = service.getCurrentUser();
        user.setId(service.getCurrentUserId());
        UserAccountExploreController userAccountController = fxmlLoader.getController();
        userAccountController.setService(this.service);

        userAccountController.setLabelText(user);
        stage.show();
    }

    public void switchToAddEventScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/addEvent.fxml"));
        Scene scene;
        root = fxmlLoader.getRoot();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        User user = service.getCurrentUser();
        user.setId(service.getCurrentUserId());
        AddEventController addEventController = fxmlLoader.getController();
        addEventController.setService(this.service);
        addEventController.setLabelText(user);
        stage.show();
    }

    public void switchToDiscoverEventsScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/discoverEvents.fxml"));
        Scene scene;
        root = fxmlLoader.getRoot();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(fxmlLoader.load(), 800, 600);
        User user = service.getCurrentUser();
        user.setId(service.getCurrentUserId());
        DiscoverEventsController discoverEventsController = fxmlLoader.getController();
        discoverEventsController.setService(this.service);
        discoverEventsController.setLabelText(user);
        stage.setScene(scene);
        stage.show();
    }

    public void showFriendRequestsDialog(Service service) throws IOException {
        this.service = service;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("views/showFriendRequests.fxml"));
        AnchorPane root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Notifications");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        ShowFriendRequestsController showFriendRequestsController = loader.getController();
        showFriendRequestsController.setService(this.service);
        dialogStage.show();
    }

    public void showMyFriendRequestsDialog(Service service) throws IOException {
        this.service = service;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("views/showMyFriendRequests.fxml"));
        AnchorPane root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Notifications");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        ShowMyFriendRequestsController showMyFriendRequestsController = loader.getController();
        showMyFriendRequestsController.setService(this.service);
        dialogStage.show();
    }

    public void switchToShowConversationScene(Service service, FriendshipDto friendshipDTO) throws IOException {
        this.service = service;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("views/showConversation.fxml"));
        AnchorPane root = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Conversation");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Scene scene;
        scene = new Scene(root);

        dialogStage.setScene(scene);

        ShowConversationController showConversationController = loader.getController();
        showConversationController.setService(this.service, friendshipDTO);
        showConversationController.setLabelText(friendshipDTO.getFirstName(), friendshipDTO.getLastName());
        dialogStage.show();
    }

    public void switchToShowPostsScene(Service service) throws IOException {
        this.service = service;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("views/posts.fxml"));

        AnchorPane root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Posts");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        PostsController postsController = loader.getController();
        postsController.setService(this.service);
        dialogStage.show();
    }

    public void switchToEditPasswordScene(Service service) throws IOException {
        this.service = service;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("views/editPassword.fxml"));

        BorderPane root = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Posts");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        EditPasswordController editPasswordController = loader.getController();
        editPasswordController.setService(this.service);
        dialogStage.show();
    }

    public void switchToDeleteConfirmationScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/confirmation.fxml"));
        Scene scene = null;
        root = fxmlLoader.getRoot();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 350, 220);

        ConfirmationController deleteConfirmationController = fxmlLoader.getController();
        deleteConfirmationController.setService(this.service);
        stage.setScene(scene);
        stage.show();
    }
}
