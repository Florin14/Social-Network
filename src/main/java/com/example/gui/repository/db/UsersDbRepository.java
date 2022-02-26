package com.example.gui.repository.db;

import com.example.gui.controller.MessageAlert;
import com.example.gui.domain.Friendship;
import com.example.gui.domain.User;
import com.example.gui.domain.validators.Validator;
import com.example.gui.repository.Repository;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersDbRepository implements Repository<Long, User> {
    protected Map<Long, User> entities;

    private final Connection connection;

    private final Validator<User> validator;

    private final FriendshipsDbRepository friendshipsDbRepository;

    public UsersDbRepository(Connection connection, FriendshipsDbRepository friendshipsDbRepository, Validator<User> validator) {
        this.connection = connection;
        this.friendshipsDbRepository = friendshipsDbRepository;
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public User save(User entity) {
        boolean exists = false;

        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");

        for (User user : findAll()) {
            if (entity.equals(user)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Sign up", "There is an account with the given email!");
            throw new IllegalArgumentException("user already exists!");
        }
        validator.validate(entity);
        String sql = "insert into users (first_name, last_name, email, password_hash, about ,path) values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getPasswordHash());
            ps.setString(5, entity.getAbout());
            ps.setString(6, entity.getPath());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user = new User(entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPasswordHash(), entity.getAbout(), entity.getPath());
        entities.put(entity.getId(), user);

        return null;
    }

    @Override
    public User delete(Long aLong) {
        if (aLong == null) {
            throw new IllegalArgumentException("deleted entity doesn't exist");
        }

        String sql = "delete from users where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, aLong);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Iterable<Friendship> friendships = friendshipsDbRepository.findAll();
        for (Friendship friendship : friendships) {
            if (friendship.getId1().equals(aLong) || friendship.getId2().equals(aLong)) {
                friendshipsDbRepository.delete(friendship.getId());
            }
        }
        entities.remove(aLong);
        return null;
    }

    @Override
    public void update(User entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        String sql = "update users set password_hash = ? where id = ?";
        String sql1 = "update users set about = ? where id = ?";
        String sql2 = "update users set path = ? where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);

            preparedStatement.setString(1, entity.getPasswordHash());
            preparedStatement.setLong(2, entity.getId());
            preparedStatement.executeUpdate();

            preparedStatement1.setString(1, entity.getAbout());
            preparedStatement1.setLong(2, entity.getId());
            preparedStatement1.executeUpdate();

            preparedStatement2.setString(1, entity.getPath());
            preparedStatement2.setLong(2, entity.getId());
            preparedStatement2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        entities.put(entity.getId(), entity);
        if (entities.get(entity.getId()) != null) {
            entities.put(entity.getId(), entity);
        }

    }

    @Override
    public User findOne(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("ID must be not null");
        String sql = "select * from users where id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, aLong);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String passwordHash = resultSet.getString("password_hash");
                String about = resultSet.getString("about");
                String path = resultSet.getString("path");

                return new User(firstName, lastName, email, passwordHash, about, path);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String passwordHash = resultSet.getString("password_hash");
                String about = resultSet.getString("about");
                String path = resultSet.getString("path");

                User user = new User(firstName, lastName, email, passwordHash, about, path);
                user.setId(id);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}