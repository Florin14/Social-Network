package com.example.gui.repository.db;

import com.example.gui.domain.Participant;
import com.example.gui.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticipantsDbRepository implements Repository<Long, Participant> {
    public Map<Participant, Long> entities;
    private final Connection connection;

    public ParticipantsDbRepository(Connection connection) {
        this.connection = connection;
        entities = new HashMap<>();
    }

    @Override
    public Participant save(Participant entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");


        String sql = "insert into participants ( event, participant) values (?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, entity.getEvent());
            ps.setLong(2, entity.getParticipant());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        entities.put(entity, entity.getId());
        return null;
    }

    @Override
    public Participant delete(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("id must be not null");
        String sql = "delete from participants where id = ?";
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
    public void update(Participant entity) {
    }

    @Override
    public Participant findOne(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("ID must be not null");
        String sql = "select * from participants where id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, aLong);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Long event = resultSet.getLong("event");
                Long participant = resultSet.getLong("participant");

                return new Participant(event, participant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Participant> findAll() {
        List<Participant> participants = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from participants");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long event = resultSet.getLong("event");
                Long participant1 = resultSet.getLong("participant");

                Participant participant = new Participant(event, participant1);
                participant.setId(id);
                participants.add(participant);
            }
            return participants;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }
}