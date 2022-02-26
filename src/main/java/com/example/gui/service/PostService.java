package com.example.gui.service;

import com.example.gui.domain.Post;
import com.example.gui.repository.Repository;

public class PostService {
    private final Repository<Long, Post> postRepository;

    public PostService(Repository<Long, Post> postRepository) {
        this.postRepository = postRepository;
    }

    public void savePost(Post post) {
        try {
            this.postRepository.save(post);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void deletePost(Long id) {
        try {
            this.postRepository.delete(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    public Post findOne(Long x) {
        try {
            return this.postRepository.findOne(x);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Iterable<Post> findAll() {
        return this.postRepository.findAll();
    }

}
