package com.buffer.lorena.bot.events;

import com.buffer.lorena.bot.DiscordService;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveAllEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveAllListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;
import org.springframework.stereotype.Component;

@Component
public class ReactionHandler implements ReactionAddListener, ReactionRemoveListener, ReactionRemoveAllListener {
    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onReactionRemoveAll(ReactionRemoveAllEvent event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onReactionRemove(ReactionRemoveEvent event) {
        throw new UnsupportedOperationException();
    }
}
