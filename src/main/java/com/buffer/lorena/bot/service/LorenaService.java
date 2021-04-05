package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.repository.LorenaRepository;
import com.buffer.lorena.db.entity.Lore;
import com.buffer.lorena.db.entity.ServerDAO;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.springframework.stereotype.Service;

@Service
public class LorenaService {
    private final LorenaConverter lorenaConverter;
    private final LorenaRepository lorenaRepository;

    public LorenaService(LorenaConverter lorenaConverter, LorenaRepository lorenaRepository) {
        this.lorenaConverter = lorenaConverter;
        this.lorenaRepository = lorenaRepository;
    }

    public void insertLore(User user, Server server, Message message){
        Lore lore = this.lorenaConverter.convertLore(user, server, message);
        lore.setLoreStatus("ACCEPTED");//TODO: rework lore logic, remove status in DB
        this.lorenaRepository.insert(lore);
    }

    public void changeServerUserVoteThreshold(Server server, int userVoteThreshold){
        ServerDAO serverDAO = this.lorenaConverter.convertServer(server);
        serverDAO.setUserVoteThreshold(userVoteThreshold);
        this.lorenaRepository.save(serverDAO);
    }
}
