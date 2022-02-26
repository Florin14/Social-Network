package com.example.gui.repository;

import com.example.gui.domain.Entity;

public interface Repository<Long, E extends Entity<Long>> {

    E findOne(Long id);

    Iterable<E> findAll();

    E save(E entity);

    E delete(Long id);

    void update(E entity);
}
