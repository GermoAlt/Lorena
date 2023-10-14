package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.entity.ServerDAO;
import com.buffer.lorena.bot.repository.ServerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * The type Lore service.
 */
@Service
public class LoreService {

    private final LorenaConverter lorenaConverter;
    private final ServerRepository serverRepository;
    private final DiscordService discordService;
    private final Environment environment;

    private static final Logger logger = LogManager.getLogger(LoreService.class);

    private static final Map<Server, Map<Long, Message>> loreMap = new HashMap<>();

    /**
     * Instantiates a new Lore service.
     *
     * @param lorenaConverter  the lorena converter
     * @param serverRepository the server repository
     * @param discordService   the discord service
     * @param environment      the environment
     */
    public LoreService(LorenaConverter lorenaConverter, ServerRepository serverRepository, DiscordService discordService, Environment environment) {
        this.lorenaConverter = lorenaConverter;
        this.serverRepository = serverRepository;
        this.discordService = discordService;
        this.environment = environment;
    }

    @PostConstruct
    private void loadLoreMap(){
        for (ServerDAO serverDAO : this.serverRepository.findAll()) {
            loadLoresFromServer(serverDAO);
        }
    }

    /**
     * Handle lore reaction.
     *
     * @param event the event
     */
    public void handleLoreReaction(ReactionAddEvent event) {
        try {
            Message message = event.requestMessage().get();
            if (!message.getAuthor().isBotUser()) {
                Reaction reaction = event.requestReaction().join().get();
                List<Reaction> reactions = reaction.getMessage().getReactions().stream()
                        .filter(r -> r.getEmoji().equalsEmoji("📜")).toList();
                ServerDAO server = this.lorenaConverter.convertServer(event.getServer().get());
                if (!reactions.isEmpty() && reactions.get(0).getCount() >= server.getUserVoteThreshold()) {
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

    /**
     * Handle lore.
     *
     * @param event the event
     */
    public void handleLore(ReactionAddEvent event) {
        Server server = event.getServer().get();

        if(!loreMap.get(server).containsKey(event.getMessageId())){
            loreMap.get(server).put(event.getMessageId(), event.getMessage().get());
            if(isProdEnvironment()){
                this.sendEmbedToLoreBoard(event);
            }
        }

        event.addReactionsToMessage("🖋");
    }

    /**
     * Send embed to lore board.
     *
     * @param event the event
     */
    public void sendEmbedToLoreBoard(ReactionAddEvent event){
        Channel channel = event.getChannel();
        Message message = event.getApi().getMessageById(event.getMessageId(), channel.asTextChannel().get()).join();
        ServerDAO server = this.lorenaConverter.convertServer(message.getServer().get());
        if(server.getLoreChannel() != null) {
            MessageAuthor author = message.getAuthor();
            EmbedBuilder embed = new EmbedBuilder()
                    .setDescription(message.getContent())
                    .setAuthor(author.getName(), null, author.getAvatar())
                    .setTimestampToNow()
                    .addInlineField("Original", "**[Jump to Message]("+buildMessageUrl(server.getIdServer(), channel.getId(), message.getId())+")**")
                    .addInlineField("Channel", "<#"+ channel.getIdAsString()+">")
                    .addField("Message ID", String.valueOf(message.getId()))
                    .setColor(Color.PINK)
                    .setImage(message.getAttachments().isEmpty() ? null : message.getAttachments().get(0).getUrl().toString());
            event.getApi().getChannelById(server.getLoreChannel()).flatMap(Channel::asTextChannel).get().sendMessage(embed);
        }
    }

    /**
     * Is prod environment boolean.
     *
     * @return the boolean
     */
    public boolean isProdEnvironment(){
        return Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("prod"));
    }

    private String buildMessageUrl(Long idServer, Long idChannel, Long idMessage){
        return "https://discord.com/channels/" +
                idServer.toString() +
                "/" +
                idChannel.toString() +
                "/" +
                idMessage.toString();
    }

    /**
     * Get lores from server map.
     *
     * @param server the server
     * @return the map
     */
    public Map<Long, Message> getLoresFromServer(Server server){
        Map<Long, Message> lores = loreMap.get(server);
        if (lores == null){
            loadLoresFromServer(this.serverRepository.findById(server.getId()).get());
        }
        return loreMap.get(server);
    }

    /**
     * Load lores from server.
     *
     * @param server the server
     */
    public void loadLoresFromServer(ServerDAO server){
        logger.info("fetching from server {}", server.getName());
        if (server.getLoreChannel() != null) {
            try {
                ServerTextChannel loreChannel = this.discordService.getDiscordApi()
                        .getServerById(server.getIdServer()).get()
                        .getChannelById(server.getLoreChannel()).get()
                        .asServerTextChannel().get();
                Set<Message> set = loreChannel.getMessagesAsStream().collect(Collectors.toSet());
                Map<Long, Message> messagePerServerMap = new HashMap<>();
                set.forEach(message -> {
                    if (message.getAuthor().getId() != message.getApi().getClientId()
                            || message.getEmbeds().isEmpty())
                        return;
                    Embed e = message.getEmbeds().get(0);
                    String urlMessage = e.getFields().get(0).getValue();

                    Long originalMessageID = Long.parseLong(
                            urlMessage.substring(
                                    urlMessage.lastIndexOf("/") + 1,
                                    urlMessage.contains("\"") ? urlMessage.indexOf("\"") - 1 : urlMessage.length() - 3));
                    messagePerServerMap.put(originalMessageID, message);
                });
                loreMap.put(
                        this.discordService.getDiscordApi().getServerById(server.getIdServer()).get(),
                        messagePerServerMap
                );
            } catch (NoSuchElementException exception) {
                logger.error("error fetching from server {}", server.getName());
            }
        } else {
            logger.warn("lore channel not set for server {}", server.getName());
        }
    }
}
