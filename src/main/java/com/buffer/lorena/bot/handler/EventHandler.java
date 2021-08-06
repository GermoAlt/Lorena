package com.buffer.lorena.bot.handler;

import com.buffer.lorena.bot.service.DiscordService;
import com.buffer.lorena.events.MessageHandler;
import com.buffer.lorena.handler.SlashCommandHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Event handler.
 */
@Component
public class EventHandler {
    /**
     * The Logger.
     */
    Logger logger = LogManager.getLogger(EventHandler.class);

    /**
     * The Discord service.
     */
    DiscordService discordService;
    /**
     * The Message handler.
     */
    MessageHandler messageHandler;
    /**
     * The Reaction handler.
     */
    ReactionHandler reactionHandler;
    /**
     * The Slash command handler.
     */
    SlashCommandHandler slashCommandHandler;

    /**
     * Instantiates a new Event handler.
     *
     * @param discordService      the discord service
     * @param messageHandler      the message handler
     * @param reactionHandler     the reaction handler
     * @param slashCommandHandler the slash command handler
     */
    @Autowired
    public EventHandler(DiscordService discordService, MessageHandler messageHandler, ReactionHandler reactionHandler, SlashCommandHandler slashCommandHandler) {
        this.discordService = discordService;
        this.messageHandler = messageHandler;
        this.reactionHandler = reactionHandler;
        this.slashCommandHandler = slashCommandHandler;
        this.addEventHandlers();
    }

    private void addEventHandlers() {
        DiscordApi api = this.discordService.getDiscordApi();
        api.addMessageCreateListener(this.messageHandler);
        api.addReactionAddListener(this.reactionHandler);
        api.addReactionRemoveListener(this.reactionHandler);
    }

}
