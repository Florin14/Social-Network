package com.example.gui;

import com.example.gui.database.DatabaseConnection;
import com.example.gui.domain.*;
import com.example.gui.domain.validators.*;
import com.example.gui.repository.Repository;
import com.example.gui.repository.db.*;
import com.example.gui.service.*;
import com.example.gui.utils.SceneUtils;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {
    Service service;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        FriendshipsDbRepository friendshipRepository1 = new FriendshipsDbRepository(connection, new FriendshipValidator());
        Repository<Long, Friendship> friendshipRepository = new FriendshipsDbRepository(connection, new FriendshipValidator());
        Repository<Long, User> userRepository = new UsersDbRepository(connection, friendshipRepository1, new UserValidator());
        Repository<Long, Message> messageRepository = new MessageDbRepository(connection, new MessageValidator());
        Repository<Long, FriendRequest> friendRequestRepository = new FriendRequestDbRepository(connection, new FriendRequestValidator());
        Repository<Long, Event> eventRepository = new EventDbRepository(connection, new EventValidator());
        Repository<Long, Post> postRepository = new PostDbRepository(connection);
        Repository<Long, Participant> participantRepository = new ParticipantsDbRepository(connection);

        UserService userService = new UserService(userRepository);
        FriendshipService friendshipService = new FriendshipService(userRepository, friendshipRepository);
        FriendRequestService friendRequestService = new FriendRequestService(friendRequestRepository, friendshipRepository);
        MessageService messageService = new MessageService(messageRepository);
        EventService eventService = new EventService(eventRepository);
        PostService postService = new PostService(postRepository);

        service = new Service(userService, friendshipService, friendRequestService, messageService, eventService, postService, participantRepository);

        SceneUtils sceneUtils = new SceneUtils();
        sceneUtils.switchToLoginScene1(stage, service);
    }
}