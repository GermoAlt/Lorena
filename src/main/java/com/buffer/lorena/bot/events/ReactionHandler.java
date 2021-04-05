package com.buffer.lorena.bot.events;

import com.buffer.lorena.bot.service.LorenaService;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReactionHandler implements ReactionAddListener, ReactionRemoveListener {

    private LorenaService lorenaService;

    @Autowired
    public ReactionHandler(LorenaService lorenaService) {
        this.lorenaService = lorenaService;
    }

    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        if(event.getEmoji().equalsEmoji("📜")){
            event.getMessage().flatMap(
                    message -> message.getReactionByEmoji("📜"))
                    .ifPresent(reaction -> {
                        if(reaction.getCount() >= 3){ //TODO:replace with uservotethreshold
                            this.lorenaService.insertLore(event.getUser().get(), event.getServer().get(), event.getMessage().get());
                        }
                    });
        };
    }

    @Override
    public void onReactionRemove(ReactionRemoveEvent event) {
        throw new UnsupportedOperationException();
    }
}
