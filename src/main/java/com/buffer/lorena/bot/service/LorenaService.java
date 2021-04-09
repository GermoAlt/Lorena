package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.repository.LorenaRepository;
import com.buffer.lorena.db.entity.Lore;
import com.buffer.lorena.db.entity.ServerDAO;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.springframework.stereotype.Service;

/**
 * The type Lorena service.
 */
@Service
public class LorenaService {
    private final LorenaConverter lorenaConverter;
    private final LorenaRepository lorenaRepository;

    /**
     * Instantiates a new Lorena service.
     *
     * @param lorenaConverter  the lorena converter
     * @param lorenaRepository the lorena repository
     */
    public LorenaService(LorenaConverter lorenaConverter, LorenaRepository lorenaRepository) {
        this.lorenaConverter = lorenaConverter;
        this.lorenaRepository = lorenaRepository;
    }

    /**
     * Insert lore.
     *
     * @param user    the user
     * @param server  the server
     * @param message the message
     */
    public void insertLore(User user, Server server, Message message){
        Lore lore = this.lorenaConverter.convertLore(user, server, message);
        this.lorenaRepository.save(lore);
    }

    /**
     * Change server user vote threshold.
     *
     * @param server            the server
     * @param userVoteThreshold the user vote threshold
     */
    public void changeServerUserVoteThreshold(Server server, int userVoteThreshold){
        ServerDAO serverDAO = this.lorenaConverter.convertServer(server);
        serverDAO.setUserVoteThreshold(userVoteThreshold);
        this.lorenaRepository.save(serverDAO);
    }
}
