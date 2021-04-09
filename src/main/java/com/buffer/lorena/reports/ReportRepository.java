package com.buffer.lorena.reports;

import com.buffer.lorena.db.entity.Lore;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The type Report repository.
 */
@Repository
public class ReportRepository {
    /**
     * Get all lore by server list.
     *
     * @param idServer the id server
     * @return the list
     */
    public List<Lore> getAllLoreByServer(Long idServer){
        throw new UnsupportedOperationException("Unimplemented feature");
    }

    /**
     * Get all lore by user list.
     *
     * @param idUser the id user
     * @return the list
     */
    public List<Lore> getAllLoreByUser(Long idUser){
        throw new UnsupportedOperationException("Unimplemented feature");
    }

    /**
     * Get all lore by user and server list.
     *
     * @param idUser   the id user
     * @param idServer the id server
     * @return the list
     */
    public List<Lore> getAllLoreByUserAndServer(Long idUser, Long idServer){
        throw new UnsupportedOperationException("Unimplemented feature");
    }
}
