package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.entity.Lore;
import com.buffer.lorena.bot.entity.ServerDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ReactionService {
    private static final Logger logger = LogManager.getLogger(ReactionService.class);
    private final LorenaConverter lorenaConverter;
    private final Environment environment;
    private final LoreService loreService;

    public ReactionService(LorenaConverter lorenaConverter, Environment environment, LoreService loreService) {
        this.lorenaConverter = lorenaConverter;
        this.environment = environment;
        this.loreService = loreService;
    }

    public void handleLoreReaction(ReactionAddEvent event) {
        try {
            if (!event.requestMessage().get().getAuthor().isBotUser()) {
                Reaction reaction = event.requestReaction().join().get();
                List<Reaction> list = reaction.getMessage().getReactions().stream()
                        .filter(r -> r.getEmoji().equalsEmoji("📜")).collect(Collectors.toList());
                ServerDAO server = this.lorenaConverter.convertServer(event.getServer().get());
                if (!list.isEmpty() && list.get(0).getCount() >= server.getUserVoteThreshold()) {
                    this.handleLore(event);
                }
            }
        }  catch (InterruptedException ie) {
            logger.error("InterruptedException: ", ie);
            Thread.currentThread().interrupt();
        } catch (ExecutionException ee) {
            logger.error("ExecutionException: ",ee);
        }
    }

    public void handleLore(ReactionAddEvent event) {
        DiscordApi discordApi = event.getApi();
        Server server = event.getServer().get();
        logger.info(server);
        Message message = discordApi.getMessageById(event.getMessageId(), event.getChannel()).join();
        logger.info(message);
        User user = message.getUserAuthor().get();
        logger.info(user);
        Lore lore = this.loreService.insertLore(user, server, message);

        boolean isNew = lore.getUpdatedAt() == null;
        if(isNew){
            logger.info("New lore: {}", lore);
            if(isProdEnvironment()){
                this.loreService.sendEmbedToLoreBoard(event);
            }
        }
        event.addReactionsToMessage("🖋");
    }

    public boolean isProdEnvironment(){
        return Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("prod"));
    }

    public void handleSendToGulagReaction(ReactionAddEvent event) {
        logger.error("send to gulag: lol this is not implemented");
    }

    public void handleFreeFromGulagReaction(ReactionAddEvent event) {
        logger.error("free from gulag: lol this is not implemented");
    }
}
