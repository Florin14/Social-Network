package com.example.gui.repository.db;

import com.example.gui.domain.FriendRequest;
import com.example.gui.domain.validators.Validator;
import com.example.gui.repository.Repository;
import com.example.gui.utils.Constants;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendRequestDbRepository implements Repository<Long, FriendRequest> {

    private final Connection connection;
    private final Validator<FriendRequest> validator;

    public FriendRequestDbRepository(Connection connection, Validator<FriendRequest> validator) {
        this.connection = connection;
        this.validator = validator;
    }

    @Override
    public FriendRequest save(FriendRequest entity) {
        boolean exists = false;
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");

        for (FriendRequest friendRequest : findAll()) {
            if (entity.equals(friendRequest) || (entity.getId1().equals(friendRequest.getId2()) && entity.getId2().equals(friendRequest.getId1()))) {
                exists = true;
                break;
            }
        }
        if (exists) {
            throw new IllegalArgumentException("friendship already exists!");
        }

        validator.validate(entity);
        String sql = "insert into friend_requests ( friend_one_id, friend_two_id, status, date) values (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getId1());
            preparedStatement.setLong(2, entity.getId2());
            preparedStatement.setString(3, String.valueOf(entity.getStatus()));
            String date1 = String.valueOf(entity.getDate());
            List<String> strings = Arrays.asList(date1.split("T"));
            List<String> atr = Arrays.asList(strings.get(1).split(":"));
            String hour = atr.get(0) + ":" + atr.get(1);
            String date = strings.get(0) + " " + hour;

            preparedStatement.setString(4, date);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public FriendRequest delete(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("id must be not null");
        String sql = "delete from friend_requests where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, aLong);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FriendRequest findOne(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");

        String sql = "select * from friend_requests where id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();

            Long friendOneId = resultSet.getLong("friend_one_id");
            Long friendTwoId = resultSet.getLong("friend_two_id");
            String status = resultSet.getString("status");
            String friendshipDate1 = resultSet.getString("date");
            LocalDateTime friendshipDate = LocalDateTime.parse(friendshipDate1, Constants.STANDARD_DATETIME_FORMAT);

            return new FriendRequest(friendOneId, friendTwoId, status, friendshipDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(FriendRequest entity) {

    }

    @Override
    public Iterable<FriendRequest> findAll() {
        List<FriendRequest> friendships = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from friend_requests");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long friendOneId = resultSet.getLong("friend_one_id");
                Long friendTwoId = resultSet.getLong("friend_two_id");
                String status = resultSet.getString("status");
                String friendshipDate1 = resultSet.getString("date");
                LocalDateTime friendshipDate = LocalDateTime.parse(friendshipDate1, Constants.STANDARD_DATETIME_FORMAT);

                FriendRequest friendship = new FriendRequest(friendOneId, friendTwoId, status, friendshipDate);
                friendship.setId(id);
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

}
