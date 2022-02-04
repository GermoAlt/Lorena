package com.buffer.lorena.bot.handler;

import com.buffer.lorena.bot.service.LorenaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Reaction handler.
 */
@Component
public class ReactionHandler implements ReactionAddListener, ReactionRemoveListener {

    private final Logger logger = LogManager.getLogger(ReactionHandler.class);

    private final LorenaService lorenaService;

    /**
     * Instantiates a new Reaction handler.
     *
     * @param lorenaService the lorena service
     */
    @Autowired
    public ReactionHandler(LorenaService lorenaService) {
        this.lorenaService = lorenaService;
    }

    /**
     * On reaction add.
     *
     * @param event the event
     */
    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        logger.info("emoji received in server {}: {}", event.getServer().map(Server::getName).get(), event.getEmoji());
        event.getEmoji().asUnicodeEmoji().ifPresent(emoji ->{
        switch (emoji){
            case "📜":
                this.lorenaService.handleLoreReaction(event);
                break;
            case "🔒":
                this.lorenaService.handleSendToGulagReaction(event);
                break;
            case "🔓":
                this.lorenaService.handleFreeFromGulagReaction(event);
                break;
            case "🔏":
                this.lorenaService.handleForceLoreReaction(event);
                break;
            default:
                break;
            }
        });
    }

    /**
     * On reaction remove.
     *
     * @param event the event
     */
    @Override
    public void onReactionRemove(ReactionRemoveEvent event) {
        logger.error("reaction removed event");
    }
}
