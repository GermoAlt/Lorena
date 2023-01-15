package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.entity.ServerDAO;
import com.buffer.lorena.bot.entity.Suggestion;
import com.buffer.lorena.bot.repository.ServerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.message.embed.EmbedImage;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Random;

/**
 * The type Message service.
 */
@Service
public class MessageService {
    private final Logger logger = LogManager.getLogger(MessageService.class);
    private final LorenaConverter lorenaConverter;
    private final LoreService loreService;
    private final ServerRepository serverRepository;
    private final Random random = new Random();

    /**
     * Instantiates a new Message service.
     *
     * @param lorenaConverter  the lorena converter
     * @param loreService      the lore service
     * @param serverRepository the server repository
     */
    public MessageService(LorenaConverter lorenaConverter, LoreService loreService, ServerRepository serverRepository) {
        this.lorenaConverter = lorenaConverter;
        this.loreService = loreService;
        this.serverRepository = serverRepository;
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
        Collection<Message> messages = loreService.getLoresFromServer(event.getServer().get()).values();

        if (!messages.isEmpty()) {
            Message m = (Message) messages.toArray()[random.nextInt(messages.size())];

            Embed e = m.getEmbeds().get(0);

            logger.info("Sending random lore");
            if(e.getImage().isPresent()) {
                EmbedImage image = e.getImage().get();
                try {
                    event.getChannel().sendMessage(
                            e.getDescription().isPresent() ? e.getDescription().get() : null,
                            image.asInputStream(event.getApi()),
                            image.getUrl().getPath().substring(image.getUrl().getPath().lastIndexOf("/")+1)
                    );
                } catch (IOException exc) {
                    logger.error(exc);
                }
            } else {
                event.getChannel().sendMessage(e.getDescription().get());
            }
        } else {
            logger.info("tried to send lore but lore map was empty");
        }
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
