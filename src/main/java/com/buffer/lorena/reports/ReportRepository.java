package com.buffer.lorena.reports;

import com.buffer.lorena.db.entity.Lore;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportRepository {
    public List<Lore> getAllLoreByServer(Long idServer){
        throw new UnsupportedOperationException("Unimplemented feature");
    }

    public List<Lore> getAllLoreByUser(Long idUser){
        throw new UnsupportedOperationException("Unimplemented feature");
    }

    public List<Lore> getAllLoreByUserAndServer(Long idUser, Long idServer){
        throw new UnsupportedOperationException("Unimplemented feature");
    }
}
