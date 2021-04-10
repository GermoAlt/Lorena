package com.buffer.lorena.bot.events;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.service.LorenaService;
import com.buffer.lorena.db.entity.ServerDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        if(event.getEmoji().equalsEmoji("📜")){
            Reaction reaction = event.requestReaction().join().get();
            List<Reaction> list =  reaction.getMessage().getReactions().stream()
                    .filter(r -> r.getEmoji().equalsEmoji("📜")).collect(Collectors.toList());
            ServerDAO server = this.lorenaConverter.convertServer(reaction.getMessage().getServer().get());
            if(list.size() >= server.getUserVoteThreshold()){
                this.lorenaService.insertLore(reaction.getMessage().getUserAuthor().get(),
                        reaction.getMessage().getServer().get(),
                        reaction.getMessage());
            }
        }
    }

    @Override
    public void onReactionRemove(ReactionRemoveEvent event) {
        logger.error("lol this is not implemented");
    }
}
