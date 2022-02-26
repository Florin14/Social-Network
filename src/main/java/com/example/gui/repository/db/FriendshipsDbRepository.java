package com.example.gui.repository.db;

import com.example.gui.domain.Friendship;
import com.example.gui.domain.validators.Validator;
import com.example.gui.repository.Repository;
import com.example.gui.utils.Constants;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendshipsDbRepository implements Repository<Long, Friendship> {

    private final Connection connection;

    private final Validator<Friendship> validator;

    public FriendshipsDbRepository(Connection connection, Validator<Friendship> validator) {
        this.connection = connection;
        this.validator = validator;

    }

    @Override
    public Friendship save(Friendship entity) {
        boolean exists = false;
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");

        for (Friendship friendship : findAll()) {
            if (entity.equals(friendship) || (entity.getId1().equals(friendship.getId2()) && entity.getId2().equals(friendship.getId1()))) {
                exists = true;
                break;
            }
        }
        if (exists) {
            throw new IllegalArgumentException("friendship already exists!");
        }
        validator.validate(entity);
        String sql = "insert into friendships ( id1, id2, friendship_date) values (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getId1());
            preparedStatement.setLong(2, entity.getId2());
            String date1 = String.valueOf(entity.getFriendshipDate());
            List<String> strings = Arrays.asList(date1.split("T"));
            List<String> atr = Arrays.asList(strings.get(1).split(":"));
            String hour = atr.get(0) + ":" + atr.get(1);
            String date = strings.get(0) + " " + hour;

            preparedStatement.setString(3, date);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Friendship delete(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("id must be not null");
        String sql = "delete from friendships where id = ?";
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
    public Friendship findOne(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");

        String sql = "select * from friendships where id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Long friendOneId = resultSet.getLong("id1");
                Long friendTwoId = resultSet.getLong("id2");
                String friendshipDate1 = resultSet.getString("friendship_date");
                LocalDateTime friendshipDate = LocalDateTime.parse(friendshipDate1, Constants.STANDARD_DATETIME_FORMAT);
                return new Friendship(friendOneId, friendTwoId, friendshipDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Friendship entity) {

    }

    @Override
    public Iterable<Friendship> findAll() {
        List<Friendship> friendships = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long friendOneId = resultSet.getLong("id1");
                Long friendTwoId = resultSet.getLong("id2");
                String friendshipDate1 = resultSet.getString("friendship_date");
                LocalDateTime friendshipDate = LocalDateTime.parse(friendshipDate1, Constants.STANDARD_DATETIME_FORMAT);
                Friendship friendship = new Friendship(friendOneId, friendTwoId, friendshipDate);
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