package com.buffer.lorena.reports;

import com.buffer.lorena.db.entity.Lore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Report service.
 */
@Service
public class ReportService {

    private ReportRepository reportRepository;

    /**
     * Instantiates a new Report service.
     *
     * @param reportRepository the report repository
     */
    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * Get lore by user list.
     *
     * @param idUser the id user
     * @return the list
     */
    public List<Lore> getLoreByUser(Long idUser){
        return reportRepository.getAllLoreByUser(idUser);
    }

    /**
     * Get lore by server list.
     *
     * @param idServer the id server
     * @return the list
     */
    public List<Lore> getLoreByServer(Long idServer){
        return reportRepository.getAllLoreByServer(idServer);
    }

    /**
     * Get lore by user and server list.
     *
     * @param idUser   the id user
     * @param idServer the id server
     * @return the list
     */
    public List<Lore> getLoreByUserAndServer(Long idUser, Long idServer){
        return reportRepository.getAllLoreByUserAndServer(idUser, idServer);
    }
}
