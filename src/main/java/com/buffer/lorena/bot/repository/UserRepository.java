package com.buffer.lorena.bot.repository;

import com.buffer.lorena.db.entity.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Lorena repository.
 *
 * @param <T> the type parameter
 */
@Repository
public interface UserRepository extends JpaRepository<UserDAO, Long> {

}
