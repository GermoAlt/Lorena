package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.entity.Lore;
import com.buffer.lorena.bot.entity.ServerDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ReactionService {
    private static final Logger logger = LogManager.getLogger(ReactionService.class);
    private final LoreService loreService;

    public ReactionService(LoreService loreService) {
        this.loreService = loreService;
    }

    public void handleLoreReaction(ReactionAddEvent event) {
        this.loreService.handleLoreReaction(event);
    }

    public void handleLore(ReactionAddEvent event) {
        this.loreService.handleLore(event);
    }

    public void handleSendToGulagReaction(ReactionAddEvent event) {
        logger.error("send to gulag: lol this is not implemented");
    }

    public void handleFreeFromGulagReaction(ReactionAddEvent event) {
        logger.error("free from gulag: lol this is not implemented");
    }
}
