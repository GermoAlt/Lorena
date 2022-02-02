package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.entity.Lore;
import com.buffer.lorena.bot.entity.LoreId;
import com.buffer.lorena.bot.entity.ServerDAO;
import com.buffer.lorena.bot.repository.LoreRepository;
import com.buffer.lorena.bot.repository.MessageRepository;
import com.buffer.lorena.bot.repository.ServerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
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
    private final MessageRepository messageRepository;
    private final LoreRepository loreRepository;
    private final DiscordService discordService;
    private final Environment environment;

    private static final Logger logger = LogManager.getLogger(LoreService.class);

    private static final Map<Server, Message[]> loreMap = new HashMap<>();

    /**
     * Instantiates a new Lore service.
     * @param lorenaConverter the lorena converter
     * @param serverRepository
     * @param loreRepository
     * @param discordService
     * @param environment     the environment
     */
    public LoreService(LorenaConverter lorenaConverter, ServerRepository serverRepository, MessageRepository messageRepository, LoreRepository loreRepository, DiscordService discordService, Environment environment) {
        this.lorenaConverter = lorenaConverter;
        this.serverRepository = serverRepository;
        this.messageRepository = messageRepository;
        this.loreRepository = loreRepository;
        this.discordService = discordService;
        this.environment = environment;
    }

    @PostConstruct
    private void loadLoreMap(){
        this.serverRepository.findAll().forEach(serverDAO -> {
            logger.info("fetching from server {}", serverDAO.getName());
            if (serverDAO.getLoreChannel() != null) {
                try {
                    ServerTextChannel loreChannel = this.discordService.getDiscordApi()
                            .getServerById(serverDAO.getIdServer()).get()
                            .getChannelById(serverDAO.getLoreChannel()).get()
                            .asServerTextChannel().get();
                    MessageSet set = loreChannel.getMessages(100000).join();
                    loreMap.put(
                            this.discordService.getDiscordApi().getServerById(serverDAO.getIdServer()).get(),
                            set.toArray(new Message[0])
                    );
                } catch (NoSuchElementException exception) {
                    logger.error("error fetching from server {}", serverDAO.getName());
                }
            } else {
                logger.warn("lore channel not set for server {}", serverDAO.getName());
            }
        });
    }

    /**
     * Handle lore reaction.
     *
     * @param event the event
     */
    public void handleLoreReaction(ReactionAddEvent event) {
        try {
            Message message = event.requestMessage().get();
            if (!message.getAuthor().isBotUser() || (message.getAuthor().isYourself() && !messageRepository.existsByMessageTextAndNotIdUser(message.getContent(), message.getAuthor().getId()))) {
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

    /**
     * Handle lore.
     *
     * @param event the event
     */
    public void handleLore(ReactionAddEvent event) {
        DiscordApi discordApi = event.getApi();
        Server server = event.getServer().get();
        logger.info(server);
        Message message = discordApi.getMessageById(event.getMessageId(), event.getChannel()).join();
        logger.info(message);
        User user = message.getUserAuthor().get();
        logger.info(user);
        Lore lore = this.insertLore(user, server, message);

        boolean isNew = lore.getUpdatedAt() == null;
        if(isNew){
            logger.info("New lore: {}", lore);
            if(isProdEnvironment()){
                this.sendEmbedToLoreBoard(event);
            }
        }
        event.addReactionsToMessage("🖋");
    }

    /**
     * Insert lore lore.
     *
     * @param user    the user
     * @param server  the server
     * @param message the message
     * @return the lore
     */
    public Lore insertLore(User user, Server server, Message message){
        return this.lorenaConverter.convertLore(user, server, message);
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
                    .setAuthor(author.getDiscriminatedName(), null, author.getAvatar())
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

    public void removeLore(ReactionAddEvent event) {
        LoreId id = new LoreId(
                event.getServer().get().getId(),
                event.getUserId(),
                event.getMessageId());
        Optional<Lore> lore = loreRepository.findById(id);
        if (lore.isPresent()) {
            loreRepository.deleteById(id);
        }
    }

    public Message[] getLoresFromServer(Server server){
        return loreMap.get(server);
    }
}
