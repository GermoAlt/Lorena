package com.buffer.lorena.bot.repository;

import com.buffer.lorena.bot.entity.Lore;
import com.buffer.lorena.bot.entity.LoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The interface Lore repository.
 */
public interface LoreRepository extends JpaRepository<Lore, LoreId> {

    /**
     * Find total lore count by id server int.
     *
     * @param idServer the id server
     * @return the int
     */
    @Query("SELECT COUNT(l) FROM Lore l WHERE l.loreId.idServer = :idServer")
    int findTotalLoreCountByIdServer(@Param("idServer") long idServer);

    /**
     * Find all by id server list.
     *
     * @param idServer the id server
     * @return the list
     */
    @Query("SELECT l FROM Lore l WHERE l.loreId.idServer = :idServer")
    List<Lore> findAllByIdServer(@Param("idServer") long idServer);

    /**
     * Find lore by id list.
     *
     * @param idServer  the id server
     * @param idUser    the id user
     * @param idMessage the id message
     * @return the list
     */
    @Query("SELECT l FROM Lore l WHERE l.loreId.idServer = :idServer AND l.loreId.idUser = :idUser AND l.loreId.idMessage = :idMessage")
    List<Lore> findLoreById(@Param("idServer") long idServer, @Param("idUser") long idUser, @Param("idMessage") long idMessage);
}
