package com.buffer.lorena.bot.events;

import com.buffer.lorena.bot.entity.MessageDAO;
import com.buffer.lorena.bot.repository.LoreRepository;
import com.buffer.lorena.bot.repository.MessageRepository;
import com.buffer.lorena.bot.service.LorenaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

/**
 * The type Message handler.
 */
@Component
public class MessageHandler implements MessageCreateListener {
    private final Logger logger = LogManager.getLogger(MessageHandler.class);
    private static final String LORENA_TEXT = "lorena";
    private final Random random = new Random();

    private LorenaService lorenaService;
    private LoreRepository loreRepository;
    private MessageRepository messageRepository;

    /**
     * Instantiates a new Message handler.
     */
    public MessageHandler() {
    }

    /**
     * Instantiates a new Message handler.
     *
     * @param lorenaService     the lorena service
     * @param loreRepository    the lore repository
     * @param messageRepository the message repository
     */
    @Autowired
    public MessageHandler(LorenaService lorenaService, LoreRepository loreRepository, MessageRepository messageRepository) {
        this.lorenaService = lorenaService;
        this.loreRepository = loreRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * On message create.
     *
     * @param event the event
     */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        logger.info("message received: {}", event.getMessageContent());
        String[] parsedMessage = event.getMessageContent().split(" ");
        if (parsedMessage[0].equalsIgnoreCase("!lore")) {
            switch (parsedMessage[1]){
                case "ping":
                    event.getChannel().sendMessage("Pong!");
                    break;
                case "votes":
                    if(event.getMessageAuthor().isServerAdmin()) {
                        try {
                            int uvt = Integer.parseInt(parsedMessage[2]);
                            this.lorenaService.changeServerUserVoteThreshold(event.getServer().get(), uvt);
                            event.addReactionsToMessage("✅");
                        } catch (Exception e) {
                            event.addReactionsToMessage("❌");
                        }
                    }
                    break;
                case "setLoreChannel":
                    if(event.getMessageAuthor().isServerAdmin()) {
                        try {
                            Long newLoreChannelId = Long.parseLong(parsedMessage[2].substring(2));
                            this.lorenaService.setServerLoreChannel(event.getServer().get(), newLoreChannelId);
                            event.addReactionsToMessage("✅");
                        } catch (Exception e) {
                            event.addReactionsToMessage("❌");
                        }
                    }
                    break;
                default:
                    event.getChannel().sendMessage("fuck off");
            }
        } else if(!lorenaService.isDevEnvironment() && event.getMessageContent().toLowerCase(Locale.ROOT).contains(LORENA_TEXT)){
            long serverId = event.getServer().get().getId();
            int totalLoreCount = loreRepository.findTotalLoreCountByIdServer(serverId);
            MessageDAO m = messageRepository.findById(loreRepository.findAllByIdServer(serverId).get(random.nextInt(totalLoreCount-1)).getIdMessage()).get();
            event.getChannel().sendMessage(m.getMessageText());
        }
    }
}
