package com.example.gui.domain;

import java.util.Objects;

public class Post extends Entity<Long>{
    private Long creator;
    private String post;

    public Post(Long creator, String post) {
        this.creator = creator;
        this.post = post;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post1 = (Post) o;
        return creator.equals(post1.creator) && post.equals(post1.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creator, post);
    }


}
