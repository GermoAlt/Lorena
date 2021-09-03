package com.buffer.lorena.bot.repository;

import com.buffer.lorena.bot.entity.MessageDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * The interface Message repository.
 */
public interface MessageRepository extends JpaRepository<MessageDAO, Long> {


    @Query("select (count(m) > 0) from MessageDAO m where m.messageText = ?1 and m.idUser <> ?2")
    boolean existsByMessageTextAndNotIdUser(String messageText, Long idUser);
}
