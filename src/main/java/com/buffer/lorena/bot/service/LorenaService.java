package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.repository.ServerRepository;
import com.buffer.lorena.bot.entity.ServerDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.*;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * The type Lorena service.
 */
@Service
public class LorenaService {
    private final Logger logger = LogManager.getLogger(LorenaService.class);

    private final LorenaConverter lorenaConverter;
    private final ServerRepository serverRepository;
    private final Environment environment;

    /**
     * Instantiates a new Lorena service.
     *
     * @param lorenaConverter  the lorena converter
     * @param serverRepository the server repository
     * @param environment      the environment
     */
    public LorenaService(LorenaConverter lorenaConverter, ServerRepository serverRepository, Environment environment) {
        this.lorenaConverter = lorenaConverter;
        this.serverRepository = serverRepository;
        this.environment = environment;
    }

    /**
     * Insert lore.
     *
     * @param user    the user
     * @param server  the server
     * @param message the message
     */
    public void insertLore(User user, Server server, Message message){
        this.lorenaConverter.convertLore(user, server, message);
    }

    /**
     * Change server user vote threshold.
     *
     * @param server            the server
     * @param userVoteThreshold the user vote threshold
     */
    public void changeServerUserVoteThreshold(Server server, int userVoteThreshold){
        ServerDAO serverDAO = this.lorenaConverter.convertServer(server);
        serverDAO.setUserVoteThreshold(userVoteThreshold);
        this.serverRepository.save(serverDAO);
    }

    /**
     * Handle lore reaction.
     *
     * @param event the event
     */
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

    private void handleLore(ReactionAddEvent event){
        if(!isDevEnvironment()) {
            this.insertLore(event.getMessageAuthor().get().asUser().get(),
                    event.getServer().get(),
                    event.getMessage().get());
        }
        this.sendEmbedToLoreBoard(event);
        event.addReactionsToMessage("🖋");
    }

    private void sendEmbedToLoreBoard(ReactionAddEvent event){
        Channel channel = event.getChannel();
        Message message = event.getApi().getMessageById(event.getMessageId(), channel.asTextChannel().get()).join();
        ServerDAO server = this.lorenaConverter.convertServer(message.getServer().get());
        if(server.getLoreChannel() != null) {
            MessageAuthor author = message.getAuthor();
            EmbedBuilder embed = new EmbedBuilder()
                    .setDescription(message.getContent())
                    .setAuthor(author.getDiscriminatedName(), null, author.getAvatar())
                    .setTimestampToNow()
                    .addInlineField("Original", "**[Jump to Message]("+buildMessageUrl(server.getIdServer(), channel.getId(), message.getId())+")**")
                    .addInlineField("Channel", "<#"+ channel.getIdAsString()+">")
                    .setColor(Color.PINK)
                    .setImage(message.getAttachments().get(0).getUrl().toString());
            event.getApi().getChannelById(server.getLoreChannel()).get().asTextChannel().get().sendMessage(embed);
        }
    }

    /**
     * Handle send to gulag reaction.
     *
     * @param event the event
     */
    public void handleSendToGulagReaction(ReactionAddEvent event) {
        logger.error("lol this is not implemented");
    }

    /**
     * Handle free from gulag reaction.
     *
     * @param event the event
     */
    public void handleFreeFromGulagReaction(ReactionAddEvent event) {
        logger.error("lol this is not implemented");
    }

    /**
     * Is dev environment boolean.
     *
     * @return the boolean
     */
    public boolean isDevEnvironment(){
        return Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("dev"));
    }

    /**
     * Handle force lore reaction.
     *
     * @param event the event
     */
    public void handleForceLoreReaction(ReactionAddEvent event) {
        try {
            if(event.getApi().getUserById(event.getUserId()).get().isBotOwner()){
                this.handleLore(event);
            }
        }  catch (InterruptedException ie) {
            logger.error("InterruptedException: ", ie);
            Thread.currentThread().interrupt();
        } catch (ExecutionException ee) {
            logger.error("ExecutionException: ",ee);
        }
    }

    /**
     * Sets server lore channel.
     *
     * @param server         the server
     * @param newLoreChannel the new lore channel
     */
    public void setServerLoreChannel(Server server, Long newLoreChannel) {
        ServerDAO serverDAO = lorenaConverter.convertServer(server);
        serverDAO.setLoreChannel(newLoreChannel);
        this.serverRepository.save(serverDAO);
    }

    /**
     * Handle reprocessing.
     *
     * @param event the event
     */
    public void handleReprocessingReaction(ReactionAddEvent event) {
        this.handleLoreReaction(event);
    }

    private String buildMessageUrl(Long idServer, Long idChannel, Long idMessage){
        return "https://discord.com/channels/" +
                idServer.toString() +
                "/" +
                idChannel.toString() +
                "/" +
                idMessage.toString();
    }
}
