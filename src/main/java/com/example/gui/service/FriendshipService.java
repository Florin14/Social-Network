package com.example.gui.service;

import com.example.gui.domain.Friendship;
import com.example.gui.domain.User;
import com.example.gui.repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FriendshipService {
    private final Repository<Long, User> userRepository;
    private final Repository<Long, Friendship> friendshipRepository;

    public FriendshipService(Repository<Long, User> userRepository, Repository<Long, Friendship> friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }

    public void saveFriend(Long currentUserId, Long friendId) {
        User userB = userRepository.findOne(friendId);
        User userA = userRepository.findOne(currentUserId);
        if (userB == null || userA == null) {
            throw new Error("User not found");
        }
        friendshipRepository.save(new Friendship(currentUserId, friendId, LocalDateTime.now()));
    }

    public void deleteFriend(Long friendshipId) {
        try {
            friendshipRepository.delete(friendshipId);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Iterable<Friendship> getUserFriendships(Long currentUserId) {
        List<Friendship> friendships = new ArrayList<>();
        for (Friendship friendship : friendshipRepository.findAll()) {
            if (friendship.getId1().equals(currentUserId) || friendship.getId2().equals(currentUserId)) {
                friendships.add(friendship);
            }
        }
        return friendships;
    }

    public int getNrOfFriends(Long aLong) {
        List<Friendship> friendships = new ArrayList<>();
        for (Friendship friendship : friendshipRepository.findAll()) {
            if (friendship.getId1().equals(aLong) || friendship.getId2().equals(aLong)) {
                friendships.add(friendship);
            }
        }
        return friendships.size();
    }

    public boolean ifFriend(Long currentUserId, Long x) {
        for (Friendship friendship : getUserFriendships(currentUserId)) {
            if ((friendship.getId1().equals(x) && friendship.getId2().equals(currentUserId)) || (friendship.getId2().equals(x) && friendship.getId1().equals(currentUserId))) {
                return true;
            }
        }
        return false;
    }

    public Friendship findOne(Long x) {
        return this.friendshipRepository.findOne(x);
    }

    public Iterable<Friendship> findAll() {
        return this.friendshipRepository.findAll();
    }
}
