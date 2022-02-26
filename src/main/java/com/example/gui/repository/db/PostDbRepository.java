package com.example.gui.repository.db;

import com.example.gui.domain.Post;
import com.example.gui.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDbRepository implements Repository<Long, Post> {
    private final Connection connection;

    public PostDbRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Post save(Post entity) {
        boolean exists = false;
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");

        for (Post post : findAll()) {
            if (entity.equals(post)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            throw new IllegalArgumentException("friendship already exists!");
        }

        String sql = "insert into posts (creator, post) values (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getCreator());
            preparedStatement.setString(2, entity.getPost());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Post delete(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("id must be not null");
        String sql = "delete from posts where id = ?";
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
    public Post findOne(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");

        String sql = "select * from posts where id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            Long creator = resultSet.getLong("creator");
            String post = resultSet.getString("post");

            return new Post(creator, post);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Post entity) {

    }

    @Override
    public Iterable<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from posts");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long creator = resultSet.getLong("creator");
                String post = resultSet.getString("post");

                Post post1 = new Post(creator, post);
                post1.setId(id);
                posts.add(post1);
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }
}
