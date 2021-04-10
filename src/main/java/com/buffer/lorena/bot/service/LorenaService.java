package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.repository.ServerRepository;
import com.buffer.lorena.bot.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ServerRepository serverRepository;

    /**
     * Instantiates a new Lorena service.
     *
     * @param lorenaConverter  the lorena converter
     * @param userRepository   the lorena repository
     * @param serverRepository the server repository
     */
    public LorenaService(LorenaConverter lorenaConverter, UserRepository userRepository,
                         ServerRepository serverRepository) {
        this.lorenaConverter = lorenaConverter;
        this.userRepository = userRepository;
        this.serverRepository = serverRepository;
    }

    /**
     * Insert lore.
     *
     * @param user    the user
     * @param server  the server
     * @param message the message
     */
    public void insertLore(User user, Server server, Message message){
        this.lorenaConverter.convertLore(user, server, message);
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
        this.serverRepository.save(serverDAO);
    }
}
