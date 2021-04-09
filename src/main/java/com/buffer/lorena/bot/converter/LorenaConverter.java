package com.buffer.lorena.bot.converter;

import com.buffer.lorena.bot.repository.LorenaRepository;
import com.buffer.lorena.db.entity.*;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * The type Lorena converter.
 */
@Component
public class LorenaConverter {

    private LorenaRepository lorenaRepository;

    /**
     * Instantiates a new Lorena converter.
     *
     * @param lorenaRepository the lorena repository
     */
    @Autowired
    public LorenaConverter(LorenaRepository lorenaRepository) {
        this.lorenaRepository = lorenaRepository;
    }

    /**
     * Convert user user dao.
     *
     * @param user the user
     * @return the user dao
     */
    public UserDAO convertUser(User user){
        Optional<UserDAO> userDAO = lorenaRepository.findById(user.getId());
        if(!userDAO.isPresent()) {
            userDAO = Optional.of(new UserDAO(user.getId(), user.getDiscriminatedName()));
            lorenaRepository.save(userDAO);
        }
        return userDAO.get();
    }

    /**
     * Convert server server dao.
     *
     * @param server the server
     * @return the server dao
     */
    public ServerDAO convertServer(Server server){
        Optional<ServerDAO> serverDAO = lorenaRepository.findById(server.getId());
        if(!serverDAO.isPresent()) {
            serverDAO = Optional.of(new ServerDAO(server.getId(), server.getName()));
            lorenaRepository.save(serverDAO.get());
        }
        return serverDAO.get();

    }

    /**
     * Convert message message dao.
     *
     * @param message the message
     * @return the message dao
     */
    public MessageDAO convertMessage(Message message){
        Optional<MessageDAO> messageDAO = lorenaRepository.findById(message.getId());
        if(!messageDAO.isPresent()){
            messageDAO = Optional.of(new MessageDAO(message.getId(), message.getContent(),
                    message.getUserAuthor().get().getId(),
                    message.getServer().get().getId()));
            lorenaRepository.save(messageDAO.get());
        }
        return messageDAO.get();
    }

    /**
     * Convert lore lore.
     *
     * @param user    the user
     * @param server  the server
     * @param message the message
     * @return the lore
     */
    public Lore convertLore(User user, Server server, Message message){
        Lore lore = new Lore(message.getId(), server.getId(), user.getId());
        lorenaRepository.save(lore);
        return lore;
    }
}
