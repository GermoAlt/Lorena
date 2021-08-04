package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.entity.MessageDAO;
import com.buffer.lorena.bot.entity.ServerDAO;
import com.buffer.lorena.bot.entity.Suggestion;
import com.buffer.lorena.bot.repository.LoreRepository;
import com.buffer.lorena.bot.repository.MessageRepository;
import com.buffer.lorena.bot.repository.ServerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Random;

/**
 * The type Message service.
 */
@Service
public class MessageService {
    private final Logger logger = LogManager.getLogger(MessageService.class);
    private final LorenaConverter lorenaConverter;
    private final ServerRepository serverRepository;
    private final LoreRepository loreRepository;
    private final MessageRepository messageRepository;
    private final Random random = new Random();

    /**
     * Instantiates a new Message service.
     *
     * @param lorenaConverter   the lorena converter
     * @param serverRepository  the server repository
     * @param loreRepository    the lore repository
     * @param messageRepository the message repository
     */
    public MessageService(LorenaConverter lorenaConverter, ServerRepository serverRepository,
                          LoreRepository loreRepository, MessageRepository messageRepository) {
        this.lorenaConverter = lorenaConverter;
        this.serverRepository = serverRepository;
        this.loreRepository = loreRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * Change server user vote threshold.
     *
     * @param server            the server
     * @param userVoteThreshold the user vote threshold
     */
    public void changeServerUserVoteThreshold(Server server, int userVoteThreshold) {
        ServerDAO serverDAO = this.lorenaConverter.convertServer(server);
        serverDAO.setUserVoteThreshold(userVoteThreshold);
        this.serverRepository.save(serverDAO);
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
     * Sets server suggestion channel.
     *
     * @param server                 the server
     * @param newSuggestionChannelId the new suggestion channel id
     */
    public void setServerSuggestionChannel(Server server, Long newSuggestionChannelId) {
        ServerDAO serverDAO = lorenaConverter.convertServer(server);
        serverDAO.setSuggestionChannel(newSuggestionChannelId);
        this.serverRepository.save(serverDAO);
    }

    /**
     * Send random lore.
     *
     * @param event the event
     */
    public void sendRandomLore(MessageCreateEvent event) {
        long serverId = event.getServer().get().getId();
        int totalLoreCount = loreRepository.findTotalLoreCountByIdServer(serverId);
        MessageDAO m = messageRepository.findById(loreRepository.findAllByIdServer(serverId).get(random.nextInt(totalLoreCount-1)).getIdMessage()).get();
        logger.info("Sending random lore: {}", m.getMessageText());
        event.getChannel().sendMessage(m.getMessageText());
    }

    /**
     * Handle suggestion.
     *
     * @param suggestion the suggestion
     */
    public void handleSuggestion(Suggestion suggestion) {
        ServerDAO serverDAO = lorenaConverter.convertServer(suggestion.getServer());

        EmbedBuilder embed = new EmbedBuilder()
                .setDescription(suggestion.getSuggestion())
                .setAuthor(suggestion.getAuthor().getDiscriminatedName(), null, suggestion.getAuthor().getAvatar())
                .setTimestampToNow()
                .setColor(Color.PINK);
        suggestion.getApi().getChannelById(serverDAO.getSuggestionChannel()).flatMap(Channel::asTextChannel).get().sendMessage(embed);
    }
}
