package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.repository.ServerRepository;
import com.buffer.lorena.bot.repository.UserRepository;
import com.buffer.lorena.db.entity.ServerDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Lorena service.
 */
@Service
public class LorenaService {
    private final Logger logger = LogManager.getLogger(LorenaService.class);

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

    public void handleLoreReaction(ReactionAddEvent event) {
        Reaction reaction = event.requestReaction().join().get();
        List<Reaction> list =  reaction.getMessage().getReactions().stream()
                .filter(r -> r.getEmoji().equalsEmoji("📜")).collect(Collectors.toList());
        ServerDAO server = this.lorenaConverter.convertServer(reaction.getMessage().getServer().get());
        if(list.size() >= server.getUserVoteThreshold()) {
            this.insertLore(reaction.getMessage().getUserAuthor().get(),
                    reaction.getMessage().getServer().get(),
                    reaction.getMessage());
        }
    }

    public void handleSendToGulagReaction(ReactionAddEvent event) {
        logger.error("lol this is not implemented");
    }

    public void handleFreeFromGulagReaction(ReactionAddEvent event) {
        logger.error("lol this is not implemented");
    }
}
