package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.entity.Suggestion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * The type Lorena service.
 */
@Service
public class LorenaService {
    private final Logger logger = LogManager.getLogger(LorenaService.class);

    private final MessageService messageService;
    private final ReactionService reactionService;
    private final Environment environment;

    /**
     * Instantiates a new Lorena service.
     *
     * @param environment     the environment
     * @param messageService  the message service
     * @param reactionService the reaction service
     */
    public LorenaService(Environment environment, MessageService messageService, ReactionService reactionService) {
        this.environment = environment;
        this.messageService = messageService;
        this.reactionService = reactionService;
    }

    /**
     * Handle lore reaction.
     *
     * @param event the event
     */
    public void handleLoreReaction(ReactionAddEvent event) {
        this.reactionService.handleLoreReaction(event);
    }


    /**
     * Handle send to gulag reaction.
     *
     * @param event the event
     */
    public void handleSendToGulagReaction(ReactionAddEvent event) {
        this.reactionService.handleSendToGulagReaction(event);
    }

    /**
     * Handle free from gulag reaction.
     *
     * @param event the event
     */
    public void handleFreeFromGulagReaction(ReactionAddEvent event) {
        this.reactionService.handleFreeFromGulagReaction(event);
    }

    /**
     * Is dev environment boolean.
     *
     * @return the boolean
     */
    public boolean isProdEnvironment(){
        return Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("prod"));
    }

    /**
     * Handle force lore reaction.
     *
     * @param event the event
     */
    public void handleForceLoreReaction(ReactionAddEvent event) {
        User user = event.getApi().getUserById(event.getUserId()).join();
        if(event.getServer().get().isAdmin(user)){
            this.reactionService.handleLore(event);
        }
    }

    /**
     * Sets server lore channel.
     *
     * @param event          the event
     * @param newLoreChannel the new lore channel
     */
    public void setServerLoreChannel(MessageCreateEvent event, String newLoreChannel) {
        if(event.getMessageAuthor().isServerAdmin()) {
            try {
                Long newLoreChannelId = Long.parseLong(newLoreChannel.substring(2, newLoreChannel.length()-1));
                this.messageService.setServerLoreChannel(event.getServer().get(), newLoreChannelId);
                event.addReactionsToMessage("✅");
            } catch (Exception e) {
                event.addReactionsToMessage("❌");
            }
        }

    }

    /**
     * Sets server suggestion channel.
     *
     * @param event                the event
     * @param newSuggestionChannel the new suggestion channel
     */
    public void setServerSuggestionChannel(MessageCreateEvent event, String newSuggestionChannel) {
        if(event.getMessageAuthor().isServerAdmin()) {
            try {
                Long newSuggestionChannelId = Long.parseLong(newSuggestionChannel.substring(2, newSuggestionChannel.length()-1));
                this.messageService.setServerSuggestionChannel(event.getServer().get(), newSuggestionChannelId);
                event.addReactionsToMessage("✅");
            } catch (Exception e) {
                event.addReactionsToMessage("❌");
            }
        }

    }

    /**
     * Send random lore.
     *
     * @param event the event
     */
    public void sendRandomLore(MessageCreateEvent event) {
        this.messageService.sendRandomLore(event);
    }

    /**
     * Change user vote threshold.
     *
     * @param event        the event
     * @param newVoteValue the new vote value
     */
    public void changeUserVoteThreshold(MessageCreateEvent event, String newVoteValue) {
        if(event.getMessageAuthor().isServerAdmin()) {
            try {
                int uvt = Integer.parseInt(newVoteValue);
                event.getServer().ifPresent(server -> {
                    this.messageService.changeServerUserVoteThreshold(server, uvt);
                    event.addReactionsToMessage("✅");
                });
            } catch (Exception e) {
                event.addReactionsToMessage("❌");
            }
        }
    }

    /**
     * Handle suggestion.
     *
     * @param suggestion the suggestion
     */
    public void handleSuggestion(Suggestion suggestion) {
        this.messageService.handleSuggestion(suggestion);
    }
}
