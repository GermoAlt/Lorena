package com.buffer.lorena.bot.repository;

import com.buffer.lorena.db.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Lorena repository.
 *
 * @param <T> the type parameter
 */
@Repository
public interface LorenaRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
}
