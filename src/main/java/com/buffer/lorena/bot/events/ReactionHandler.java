package com.buffer.lorena.bot.events;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.service.LorenaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private LorenaService lorenaService;
    private LorenaConverter lorenaConverter;

    /**
     * Instantiates a new Reaction handler.
     *
     * @param lorenaService   the lorena service
     * @param lorenaConverter the lorena converter
     */
    @Autowired
    public ReactionHandler(LorenaService lorenaService, LorenaConverter lorenaConverter) {
        this.lorenaService = lorenaService;
        this.lorenaConverter = lorenaConverter;
    }

    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        switch (event.getEmoji().asUnicodeEmoji().get()){
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
            case "🔄":
                this.lorenaService.handleReprocessingReaction(event);
                break;
        }
    }

    @Override
    public void onReactionRemove(ReactionRemoveEvent event) {
        logger.error("lol this is not implemented");
    }
}
