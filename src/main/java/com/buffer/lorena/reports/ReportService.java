package com.buffer.lorena.reports;

import com.buffer.lorena.db.entity.Lore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Lore> getLoreByUser(Long idUser){
        return reportRepository.getAllLoreByUser(idUser);
    }

    public List<Lore> getLoreByServer(Long idServer){
        return reportRepository.getAllLoreByServer(idServer);
    }

    public List<Lore> getLoreByUserAndServer(Long idUser, Long idServer){
        return reportRepository.getAllLoreByUserAndServer(idUser, idServer);
    }
}
