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
        if (parsedMessage[0].equalsIgnoreCase("!lore") || parsedMessage[0].equalsIgnoreCase("!lorebot")) {
            switch (parsedMessage[1].toLowerCase(Locale.ROOT)){
                case "ping":
                    event.getChannel().sendMessage("Pong!");
                    break;
                case "votes":
                    if(event.getMessageAuthor().isServerAdmin()) {
                        try {
                            int uvt = Integer.parseInt(parsedMessage[2]);
                            event.getServer().ifPresent(server -> {
                                this.lorenaService.changeServerUserVoteThreshold(server, uvt);
                                event.addReactionsToMessage("✅");
                            });
                        } catch (Exception e) {
                            event.addReactionsToMessage("❌");
                        }
                    }
                    break;
                case "setlorechannel":
                    if(event.getMessageAuthor().isServerAdmin()) {
                        try {
                            Long newLoreChannelId = Long.parseLong(parsedMessage[2].substring(2, parsedMessage[2].length()-1));
                            this.lorenaService.setServerLoreChannel(event.getServer().get(), newLoreChannelId);
                            event.addReactionsToMessage("✅");
                        } catch (Exception e) {
                            event.addReactionsToMessage("❌");
                        }
                    }
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
