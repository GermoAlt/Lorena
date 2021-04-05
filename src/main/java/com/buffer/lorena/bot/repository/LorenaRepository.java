package com.buffer.lorena.bot.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class LorenaRepository{
    private final EntityManager entityManager;

    public LorenaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Object entity){
        entityManager.merge(entity);
    }

    public Object getById(Object o, Long id){
        return entityManager.find(o.getClass(), id);
    }

    public void insert(Object o){
        entityManager.persist(o);
    }
}
