package com.example.gui.repository.db;

import com.example.gui.domain.Event;
import com.example.gui.domain.validators.Validator;
import com.example.gui.repository.Repository;
import com.example.gui.utils.Constants;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class EventDbRepository implements Repository<Long, Event> {
    public Map<Event, Long> entities;
    private final Connection connection;

    private final Validator<Event> validator;

    public EventDbRepository(Connection connection, Validator<Event> validator) {
        this.connection = connection;
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public Event save(Event entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);

        String sql = "insert into events ( creator, name, place, type, description, date) values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, entity.getCreator());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getPlace());
            ps.setString(4, entity.getType());
            ps.setString(5, entity.getDescription());
            String date1 = String.valueOf(entity.getDate());
            LocalDate date = LocalDate.parse(date1,Constants.STANDARD_DATE_FORMAT);
            ps.setDate(6, Date.valueOf(date));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        entities.put(entity,entity.getId());
        return null;
    }

    @Override
    public Event delete(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("id must be not null");
        String sql = "delete from events where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, aLong);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        entities.remove(findOne(aLong));
        return null;
    }

    @Override
    public void update(Event entity) {
    }

    @Override
    public Event findOne(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("ID must be not null");
        String sql = "select * from events where id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, aLong);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                Long creator = resultSet.getLong("creator");
                String name = resultSet.getString("name");
                String place = resultSet.getString("place");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                String date1 = resultSet.getString("date");
                LocalDate date = LocalDate.parse(date1, Constants.STANDARD_DATE_FORMAT);

                return new Event(creator, name, place, type, description, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Event> findAll() {
        List<Event> events = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from events");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long creator = resultSet.getLong("creator");
                String name = resultSet.getString("name");
                String place = resultSet.getString("place");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                String date1 = resultSet.getString("date");
                LocalDate date = LocalDate.parse(date1, Constants.STANDARD_DATE_FORMAT);

                Event event = new Event(creator, name, place, type, description, date);
                event.setId(id);
                events.add(event);
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}

