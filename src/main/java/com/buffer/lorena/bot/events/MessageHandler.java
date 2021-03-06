package com.buffer.lorena.bot.events;

import com.buffer.lorena.bot.service.LorenaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * The type Message handler.
 */
@Component
public class MessageHandler implements MessageCreateListener {
    private final Logger logger = LogManager.getLogger(MessageHandler.class);
    private static final String LORENA_TEXT = "lorena";

    private LorenaService lorenaService;

    /**
     * Instantiates a new Message handler.
     */
    public MessageHandler() {
    }

    /**
     * Instantiates a new Message handler.
     *
     * @param lorenaService     the lorena service
     */
    @Autowired
    public MessageHandler(LorenaService lorenaService) {
        this.lorenaService = lorenaService;
    }

    /**
     * On message create.
     *
     * @param event the event
     */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        logger.info("message received in server {}: {}", event.getServer().map(Server::getName).get(), event.getMessageContent());
        String[] parsedMessage = event.getMessageContent().split(" ");
        if ((parsedMessage[0].equalsIgnoreCase("!lore") || parsedMessage[0].equalsIgnoreCase("!lorebot")) && !event.getMessageAuthor().isBotUser()) {
            switch (parsedMessage[1].toLowerCase(Locale.ROOT)){
                case "ping":
                    event.getChannel().sendMessage("Pong!");
                    break;
                case "votes":
                    this.lorenaService.changeUserVoteThreshold(event, parsedMessage[2]);
                    break;
                case "setlorechannel":
                    this.lorenaService.setServerLoreChannel(event, parsedMessage[2]);
                    break;
                case "dolore":
                    this.lorenaService.sendRandomLore(event);
                    break;
                default:
                    event.getChannel().sendMessage("fuck off");
            }
        } else if(event.getMessageContent().toLowerCase(Locale.ROOT).contains(LORENA_TEXT)){
            this.lorenaService.sendRandomLore(event);
        }
    }
}
