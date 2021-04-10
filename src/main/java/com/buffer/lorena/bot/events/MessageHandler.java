package com.buffer.lorena.bot.events;

import com.buffer.lorena.bot.service.LorenaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Message handler.
 */
@Component
public class MessageHandler implements MessageCreateListener {
    private final Logger logger = LogManager.getLogger("MessageHandler");

    private LorenaService lorenaService;

    /**
     * Instantiates a new Message handler.
     */
    public MessageHandler() {
    }

    /**
     * Instantiates a new Message handler.
     *
     * @param lorenaService the lorena service
     */
    @Autowired
    public MessageHandler(LorenaService lorenaService) {
        this.lorenaService = lorenaService;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        logger.info("message received: " + event.getMessageContent());
        String[] parsedMessage = event.getMessageContent().split(" ");
        if (parsedMessage[0].equalsIgnoreCase("!lore")) {
            switch (parsedMessage[1]){
                case "ping":
                    event.getChannel().sendMessage("Pong!");
                    break;
                case "votes":
                    try{
                        int uvt = Integer.parseInt(parsedMessage[2]);
                        this.lorenaService.changeServerUserVoteThreshold(event.getServer().get(), uvt);
                        event.addReactionsToMessage("✅");
                    } catch (Exception e) {
                        event.addReactionsToMessage("❌");
                    }
                    break;
                default:
                    event.getChannel().sendMessage("fuck off");
            }

        }
    }
}
