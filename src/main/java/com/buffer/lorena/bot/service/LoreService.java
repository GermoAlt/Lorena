package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.entity.Lore;
import com.buffer.lorena.bot.entity.ServerDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.Reaction;
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

@Service
public class LoreService {

    private final LorenaConverter lorenaConverter;

    private final Environment environment;
    private static final Logger logger = LogManager.getLogger(LoreService.class);

    public LoreService(LorenaConverter lorenaConverter, Environment environment) {
        this.lorenaConverter = lorenaConverter;
        this.environment = environment;
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

    public Lore insertLore(User user, Server server, Message message){
        return this.lorenaConverter.convertLore(user, server, message);
    }

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
                    .setColor(Color.PINK)
                    .setImage(message.getAttachments().isEmpty() ? null : message.getAttachments().get(0).getUrl().toString());
            event.getApi().getChannelById(server.getLoreChannel()).flatMap(Channel::asTextChannel).get().sendMessage(embed);
        }
    }

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
}
