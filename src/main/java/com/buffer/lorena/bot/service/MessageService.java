package com.buffer.lorena.bot.service;

import com.buffer.lorena.bot.converter.LorenaConverter;
import com.buffer.lorena.bot.entity.MessageDAO;
import com.buffer.lorena.bot.entity.ServerDAO;
import com.buffer.lorena.bot.repository.LoreRepository;
import com.buffer.lorena.bot.repository.MessageRepository;
import com.buffer.lorena.bot.repository.ServerRepository;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MessageService {

    private final LorenaConverter lorenaConverter;
    private final ServerRepository serverRepository;
    private final LoreRepository loreRepository;
    private final MessageRepository messageRepository;
    private final Random random = new Random();

    public MessageService(LorenaConverter lorenaConverter, ServerRepository serverRepository,
                          LoreRepository loreRepository, MessageRepository messageRepository) {
        this.lorenaConverter = lorenaConverter;
        this.serverRepository = serverRepository;
        this.loreRepository = loreRepository;
        this.messageRepository = messageRepository;
    }

    public void changeServerUserVoteThreshold(Server server, int userVoteThreshold) {
        ServerDAO serverDAO = this.lorenaConverter.convertServer(server);
        serverDAO.setUserVoteThreshold(userVoteThreshold);
        this.serverRepository.save(serverDAO);
    }

    public void setServerLoreChannel(Server server, Long newLoreChannel) {
        ServerDAO serverDAO = lorenaConverter.convertServer(server);
        serverDAO.setLoreChannel(newLoreChannel);
        this.serverRepository.save(serverDAO);
    }

    public void sendRandomLore(MessageCreateEvent event) {
        long serverId = event.getServer().get().getId();
        int totalLoreCount = loreRepository.findTotalLoreCountByIdServer(serverId);
        MessageDAO m = messageRepository.findById(loreRepository.findAllByIdServer(serverId).get(random.nextInt(totalLoreCount-1)).getIdMessage()).get();
        event.getChannel().sendMessage(m.getMessageText());
    }
}
