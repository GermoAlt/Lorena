package com.buffer.lorena.bot.converter;

import com.buffer.lorena.bot.repository.LoreRepository;
import com.buffer.lorena.bot.repository.MessageRepository;
import com.buffer.lorena.bot.repository.ServerRepository;
import com.buffer.lorena.bot.repository.UserRepository;
import com.buffer.lorena.bot.entity.*;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

/**
 * The type Lorena converter.
 */
@Component
public class LorenaConverter {

    private final ServerRepository serverRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final LoreRepository loreRepository;


    /**
     * Instantiates a new Lorena converter.
     *
     * @param serverRepository  the server repository
     * @param userRepository    the user repository
     * @param messageRepository the message repository
     * @param loreRepository    the lore repository
     */
    public LorenaConverter(ServerRepository serverRepository, UserRepository userRepository,
                           MessageRepository messageRepository, LoreRepository loreRepository) {
        this.serverRepository = serverRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.loreRepository = loreRepository;
    }

    /**
     * Convert user user dao.
     *
     * @param user the user
     * @return the user dao
     */
    public UserDAO convertUser(User user){
        Optional<UserDAO> userDAO = userRepository.findById(user.getId());
        if(userDAO.isEmpty()) {
            userDAO = Optional.of(new UserDAO(user.getId(), user.getDiscriminatedName()));
        } else {
            if (userDAO.get().getCreatedAt() == null) userDAO.get().setCreatedAt(Timestamp.from(Instant.now()));
            userDAO.get().setUpdatedAt(Timestamp.from(Instant.now()));
        }
        return userRepository.saveAndFlush(userDAO.get());
    }

    /**
     * Convert server server dao.
     *
     * @param server the server
     * @return the server dao
     */
    public ServerDAO convertServer(Server server){
        Optional<ServerDAO> serverDAO = serverRepository.findById(server.getId());
        if(serverDAO.isEmpty()) {
            serverDAO = Optional.of(new ServerDAO(server.getId(), server.getName()));
        } else {
            if (serverDAO.get().getCreatedAt() == null) serverDAO.get().setCreatedAt(Timestamp.from(Instant.now()));
            serverDAO.get().setUpdatedAt(Timestamp.from(Instant.now()));
        }
        return serverRepository.saveAndFlush(serverDAO.get());

    }

    /**
     * Convert message message dao.
     *
     * @param message the message
     * @return the message dao
     */
    public MessageDAO convertMessage(Message message){
        Optional<MessageDAO> messageDAO = messageRepository.findById(message.getId());
        if(messageDAO.isEmpty()){
            messageDAO = Optional.of(new MessageDAO(message.getId(), message.getContent(),
                    message.getUserAuthor().get().getId(),
                    message.getServer().get().getId()));
        } else {
            if (messageDAO.get().getCreatedAt() == null) messageDAO.get().setCreatedAt(Timestamp.from(Instant.now()));
            messageDAO.get().setUpdatedAt(Timestamp.from(Instant.now()));
        }
        return messageRepository.saveAndFlush(messageDAO.get());
    }

    /**
     * Convert lore.
     *
     * @param user    the user
     * @param server  the server
     * @param message the message
     * @return the lore
     */
    public Lore convertLore(User user, Server server, Message message){
        LoreId id = new LoreId(
                convertServer(server).getIdServer(),
                convertUser(user).getIdUser(),
                convertMessage(message).getIdMessage());
        Optional<Lore> lore = loreRepository.findById(id);
        Lore loreResult = null;
        if(lore.isEmpty()){
            loreResult = new Lore(id);
            loreResult = loreRepository.saveAndFlush(loreResult);
        } else {
            loreResult = lore.get();
            if (loreResult.getCreatedAt() == null) loreResult.setCreatedAt(Timestamp.from(Instant.now()));
            loreResult.setUpdatedAt(Timestamp.from(Instant.now()));
            loreResult = loreRepository.saveAndFlush(loreResult);
        }
        return loreResult;
    }
}
