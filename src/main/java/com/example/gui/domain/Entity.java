package com.example.gui.domain;

import java.io.Serial;
import java.io.Serializable;

public class Entity<ID> implements Serializable {
    protected ID id;
    @Serial
    private static final long serialVersionUID = 7331115341259248461L;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}

