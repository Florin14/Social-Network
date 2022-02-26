package com.example.gui.service;

import com.example.gui.domain.*;
import com.example.gui.domain.validators.ValidationException;
import com.example.gui.repository.Repository;

import java.time.LocalDate;
import java.util.*;

public class Service {
    private final UserService userService;
    private final FriendshipService friendshipService;
    private final FriendRequestService friendRequestService;
    private final MessageService messageService;
    private final EventService eventService;
    private final PostService postService;
    private final Repository<Long, Participant> participantRepository;

    private Long currentUserId = 0L;
    private User currentUser;

    public Service(UserService userService, FriendshipService friendshipService, FriendRequestService friendRequestService, MessageService messageService, EventService eventService, PostService postService, Repository<Long, Participant> participantRepository) {
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.friendRequestService = friendRequestService;
        this.messageService = messageService;
        this.eventService = eventService;
        this.postService = postService;
        this.participantRepository = participantRepository;
    }

    public void loginUser(String email, String password) {
        for (User user : userService.findAll()) {
            if (user.getEmail().equals(email) && user.getPasswordHash().equals(password)) {
                user.setId(user.getId());
                currentUserId = user.getId();
                currentUser = userService.findOne(currentUserId);
            }
        }
    }

    public boolean createUser(String firstName, String lastName, String email, String password) {
        boolean validatedSignUp = false;
        try {
            User newUser = new User(firstName, lastName, email, password, "", "");
            newUser.setId(newUser.getId());
            userService.saveUser(newUser);
            validatedSignUp = true;
            currentUserId = newUser.getId();
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        loginUser(email, password);
        return validatedSignUp;
    }

    public void deleteAccount() {
        for (User user : userService.findAll()) {
            if (user.equals(userService.findOne(currentUserId))) {
                currentUserId = user.getId();
                if (currentUserId == null) {
                    return;
                }

                for (Friendship friendship : friendshipService.getUserFriendships(currentUserId)) {
                    friendshipService.deleteFriend(friendship.getId());
                }

                for (FriendRequest friendRequest : printMyFriendRequests()) {
                    friendRequestService.deleteFriendRequests(friendRequest.getId());
                }

                for (Message message : printAllMessages()) {
                    if (message.getFrom().equals(currentUserId) || message.getTo().equals(currentUserId)) {
                        messageService.removeMessage(message.getId());
                    }
                }

                for (Post post : printAllPosts()) {
                    if (post.getCreator().equals(currentUserId)) {
                        postService.deletePost(post.getId());
                    }
                }

                for (Event event : printAllEvents()) {
                    if (event.getCreator().equals(currentUserId)) {
                        eventService.deleteEvent(event.getId());
                    }
                }
                userService.deleteUser(currentUserId);
                currentUserId = null;
            }
        }
    }

    public void updateUser(String newPassword, String newAbout, String newPath) {
        if (!newPassword.equals("")) {
            userService.updateUser(currentUserId, currentUser.getFirstName(), currentUser.getLastName(), currentUser.getEmail(), newPassword, currentUser.getAbout(), currentUser.getPath());
        } else if (!newAbout.equals("")) {
            userService.updateUser(currentUserId, currentUser.getFirstName(), currentUser.getLastName(), currentUser.getEmail(), currentUser.getPasswordHash(), newAbout, currentUser.getPath());
        } else if (!newPath.equals("")) {
            userService.updateUser(currentUserId, currentUser.getFirstName(), currentUser.getLastName(), currentUser.getEmail(), currentUser.getPasswordHash(), currentUser.getAbout(), newPath);
        }
    }

    public void logoutUser() {
        currentUserId = null;
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public User getCurrentUser() {
        return userService.findOne(currentUserId);
    }

    public User findOne(Long x) {
        return this.userService.findOne(x);
    }

    public Iterable<User> getAllUsers() {
        Iterable<User> allUsers = userService.findAll();
        List<User> getAll = new ArrayList<>();
        for (User user : allUsers) {
            if (!user.equals(currentUser)) {
                getAll.add(user);
            }
        }
        return getAll;
    }

    //CRUD FRIENDSHIP
    public void removeFriend(Long friendshipId) {
        if (friendshipId == null) {
            throw new Error("Id not found");
        }
        friendshipService.deleteFriend(friendshipId);
    }

    public Iterable<Friendship> getUserFriendships() {
        List<Friendship> friendships = new ArrayList<>();
        for (Friendship friendship : friendshipService.findAll()) {
            if (friendship.getId1().equals(currentUserId) || friendship.getId2().equals(currentUserId)) {
                friendships.add(friendship);
            }
        }
        return friendships;
    }

    public Iterable<Friendship> getAllFriendships() {
        return friendshipService.findAll();

    }

    public int getNrOfFriends(Long aLong) {
        return friendshipService.getNrOfFriends(aLong);
    }

    public boolean ifFriend(Long x) {
        return friendshipService.ifFriend(currentUserId, x);
    }

    //CRUD MESSAGE
    public void sendMessage(Long from, Long to, String text) {
        try {
            this.messageService.saveMessage(from, to, text);
        } catch (ValidationException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage(Long id) {
        messageService.removeMessage(id);
    }

    public Iterable<Message> printAllMessages() {
        return messageService.findAll();
    }

    public List<Message> showConversation(Long id1, Long id2) {
        return messageService.showConversation(id1, id2);
    }

    //CRUD FRIEND REQUEST
    public void sendFriendRequest(Long id) {
        try {
            friendRequestService.sendFriendRequest(currentUserId, id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Iterable<FriendRequest> printMyFriendRequests() {
        Iterable<FriendRequest> friendRequests = this.friendRequestService.printAllFriendRequests();
        List<FriendRequest> myFriendRequests = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequests) {
            if (friendRequest.getId1().equals(currentUserId) || friendRequest.getId2().equals(currentUserId)) {
                myFriendRequests.add(friendRequest);
            }
        }
        return myFriendRequests;
    }

    public void deleteFriendRequests(Long id) {
        this.friendRequestService.deleteFriendRequests(id);
    }

    public void updateFriendRequest(Long id, Status status) {
        friendRequestService.updateFriendRequest(id, status);
    }

    //CRUD POST

    public void savePost(String text) {
        Post post = new Post(currentUserId, text);
        postService.savePost(post);
    }

    public void deletePost(Long id) {
        postService.deletePost(id);
    }

    public Iterable<Post> printAllPosts() {
        List<Post> currentUserPosts = new ArrayList<>();
        for (Post post : postService.findAll()) {
            if (post.getCreator().equals(currentUserId)) {
                currentUserPosts.add(post);
            }
        }
        return currentUserPosts;
    }

    //CRUD EVENT

    public void saveEvent(String eventName, String eventLocation, String category, String description, LocalDate date) {
        Event event = new Event(currentUserId, eventName, eventLocation, category, description, date);
        eventService.saveEvent(event);
    }

    public void deleteEvent(Long id) {
        eventService.deleteEvent(id);
    }

    public Iterable<Event> printAllEvents() {
        return eventService.findAll();
    }

    public boolean verifyParticipant(Event event, Long id) {
        for (Participant part : participantRepository.findAll()) {
            if (part.getParticipant().equals(id) && part.getEvent().equals(event.getId()))
                return true;
        }
        return false;
    }

    /// CRUD PARTICIPANT
    public void saveParticipant(Long event) {
        Participant participant = new Participant(event, currentUserId);
        participantRepository.save(participant);
    }

    public void deleteParticipant(Long event) {
        Long id = 0L;
        for (Participant part : participantRepository.findAll()) {
            if (part.getParticipant().equals(currentUserId) && part.getEvent().equals(event)) {
                id = part.getId();
            }
        }
        participantRepository.delete(id);
    }

    public Iterable<Participant> findAllParticipants() {
        return participantRepository.findAll();
    }

    public int nrOfParticipants(Event event) {
        int nrOfParticipants = 1;
        for (Participant participant : findAllParticipants()) {
            if (event.getId().equals(participant.getEvent())) {
                nrOfParticipants++;
            }
        }
        return nrOfParticipants;
    }

    public void savePath(String path) {
        String newPath;
        if (currentUser.getPath() == null) {
            newPath = path + ",";
        } else {
            newPath = path + "," + currentUser.getPath();
        }
        updateUser("", "", newPath);
    }
}







