package com.buffer.lorena.bot;

import com.buffer.lorena.bot.events.MessageHandler;
import com.buffer.lorena.bot.events.ReactionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {
    Logger logger = LogManager.getLogger(EventHandler.class);

    DiscordService discordService;
    MessageHandler messageHandler;
    ReactionHandler reactionHandler;

    @Autowired
    public EventHandler(DiscordService discordService, MessageHandler messageHandler, ReactionHandler reactionHandler) {
        this.discordService = discordService;
        this.messageHandler = messageHandler;
        this.reactionHandler = reactionHandler;
        this.addEventHandlers();
    }

    private void addEventHandlers() {
        DiscordApi api = this.discordService.getDiscordApi();
        api.addMessageCreateListener(this.messageHandler);
        api.addReactionAddListener(this.reactionHandler);
        api.addReactionRemoveListener(this.reactionHandler);
        api.addReactionRemoveAllListener(this.reactionHandler);
    }
}
