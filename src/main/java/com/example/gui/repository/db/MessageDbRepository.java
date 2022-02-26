package com.example.gui.repository.db;

import com.example.gui.domain.Message;

import com.example.gui.domain.validators.Validator;
import com.example.gui.repository.Repository;
import com.example.gui.utils.Constants;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class MessageDbRepository implements Repository<Long, Message> {

    private final Connection connection;
    private final Validator<Message> validator;

    public MessageDbRepository(Connection connection, Validator<Message> validator) {
        this.connection = connection;
        this.validator = validator;
    }

    @Override
    public Message save(Message entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        String sql = "insert into messages (from_user, to_user, message, message_date, reply ) values (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, entity.getFrom());
            ps.setLong(2, entity.getTo());
            ps.setString(3, entity.getMessage());
            String date1 = String.valueOf(entity.getDate());
            List<String> strings = Arrays.asList(date1.split("T"));
            List<String> atr = Arrays.asList(strings.get(1).split(":"));
            String hour = atr.get(0) + ":" + atr.get(1);
            String date = strings.get(0) + " " + hour;
            ps.setString(4, date);
            ps.setLong(5, entity.getReply());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message delete(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("id must be not null");
        String sql = "delete from messages where id = ?";
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
    public void update(Message entity) {
    }

    @Override
    public Message findOne(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("ID must be not null");
        String sql = "select * from messages where id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, aLong);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();

            Long from = resultSet.getLong("from_user");
            Long to = resultSet.getLong("to_user");
            String message = resultSet.getString("message");
            String date1 = resultSet.getString("message_date");
            LocalDateTime date = LocalDateTime.parse(date1, Constants.STANDARD_DATETIME_FORMAT);
            Long reply = resultSet.getLong("reply");

            return new Message(from, to, message, date, reply);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from messages");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long from = resultSet.getLong("from_user");
                Long to = resultSet.getLong("to_user");
                String message = resultSet.getString("message");
                String date1 = resultSet.getString("message_date");
                LocalDateTime date = LocalDateTime.parse(date1, Constants.STANDARD_DATETIME_FORMAT);
                Long reply = resultSet.getLong("reply");
                Message message1 = new Message(from, to, message, date, reply);
                message1.setId(id);
                messages.add(message1);
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}