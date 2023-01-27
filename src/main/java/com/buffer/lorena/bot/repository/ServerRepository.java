package com.buffer.lorena.bot.repository;

import com.buffer.lorena.bot.entity.ServerDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The interface Server repository.
 */
public interface ServerRepository extends MongoRepository<ServerDAO, Long> {
}
