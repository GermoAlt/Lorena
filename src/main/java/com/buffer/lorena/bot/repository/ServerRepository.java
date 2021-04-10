package com.buffer.lorena.bot.repository;

import com.buffer.lorena.db.entity.MessageDAO;
import com.buffer.lorena.db.entity.ServerDAO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Server repository.
 */
public interface ServerRepository extends JpaRepository<ServerDAO, Long> {
}
