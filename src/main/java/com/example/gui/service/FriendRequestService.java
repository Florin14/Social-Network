package com.example.gui.service;

import com.example.gui.domain.FriendRequest;
import com.example.gui.domain.Friendship;
import com.example.gui.domain.Status;
import com.example.gui.repository.Repository;

import java.time.LocalDateTime;

public class FriendRequestService {
    private final Repository<Long, FriendRequest> friendRequestDbRepository;
    private final Repository<Long, Friendship> friendshipDbRepository;

    public FriendRequestService(Repository<Long, FriendRequest> friendRequestDbRepository, Repository<Long, Friendship> friendshipDbRepository) {
        this.friendRequestDbRepository = friendRequestDbRepository;
        this.friendshipDbRepository = friendshipDbRepository;
    }

    public void sendFriendRequest(Long id1, Long id2) {
        boolean ok = true;
        if (id1 == null || id2 == null) {
            throw new IllegalArgumentException("Id's must be not null");
        }
        Iterable<Friendship> getAll = friendshipDbRepository.findAll();
        for (Friendship friendship : getAll) {
            if ((friendship.getId1().equals(id1) && friendship.getId2().equals(id2)) || friendship.getId1().equals(id2) && friendship.getId2().equals(id1)) {
                ok = false;
                break;
            }
        }
        if (ok) {
            String status = String.valueOf(Status.PENDING);
            try {
                this.friendRequestDbRepository.save(new FriendRequest(id1, id2, status, LocalDateTime.now()));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("The friend request can't be sent!Friendship already exists");
        }
    }

    public Iterable<FriendRequest> printAllFriendRequests() {
        return this.friendRequestDbRepository.findAll();

    }

    public FriendRequest findOne(Long x) {
        try {
            return this.friendRequestDbRepository.findOne(x);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void deleteFriendRequests(Long id) {
        try {
            this.friendRequestDbRepository.delete(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void updateFriendRequest(Long id, Status status) {
        FriendRequest friendRequest = this.friendRequestDbRepository.findOne(id);
        if (status == Status.REJECTED) {
            this.friendRequestDbRepository.delete(id);
        } else if (status == Status.APPROVED) {
            deleteFriendRequests(id);
            Friendship friendship = new Friendship(friendRequest.getId1(), friendRequest.getId2(), LocalDateTime.now());
            friendshipDbRepository.save(friendship);
        }
    }

}
