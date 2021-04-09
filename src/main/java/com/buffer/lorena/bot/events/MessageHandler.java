package com.buffer.lorena.bot.events;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.stereotype.Component;

/**
 * The type Message handler.
 */
@Component
public class MessageHandler implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equalsIgnoreCase("!lore ping")) {
            event.getChannel().sendMessage("Pong!");
        }
    }
}
