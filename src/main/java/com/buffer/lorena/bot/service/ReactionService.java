package com.buffer.lorena.bot.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.springframework.stereotype.Service;


/**
 * The type Reaction service.
 */
@Service
public class ReactionService {
    private static final Logger logger = LogManager.getLogger(ReactionService.class);
    private final LoreService loreService;

    /**
     * Instantiates a new Reaction service.
     *
     * @param loreService the lore service
     */
    public ReactionService(LoreService loreService) {
        this.loreService = loreService;
    }

    /**
     * Handle lore reaction.
     *
     * @param event the event
     */
    public void handleLoreReaction(ReactionAddEvent event) {
        this.loreService.handleLoreReaction(event);
    }

    /**
     * Handle lore.
     *
     * @param event the event
     */
    public void handleLore(ReactionAddEvent event) {
        this.loreService.handleLore(event);
    }

    /**
     * Handle send to gulag reaction.
     *
     * @param event the event
     */
    public void handleSendToGulagReaction(ReactionAddEvent event) {
        logger.error("send to gulag: lol this is not implemented");
    }

    /**
     * Handle free from gulag reaction.
     *
     * @param event the event
     */
    public void handleFreeFromGulagReaction(ReactionAddEvent event) {
        logger.error("free from gulag: lol this is not implemented");
    }
}
