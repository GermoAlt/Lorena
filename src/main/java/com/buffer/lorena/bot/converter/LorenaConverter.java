package com.buffer.lorena.bot.converter;

import com.buffer.lorena.db.entity.Lore;
import com.buffer.lorena.db.entity.MessageDAO;
import com.buffer.lorena.db.entity.ServerDAO;
import com.buffer.lorena.db.entity.UserDAO;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class LorenaConverter {

    //TODO: THIS SHOULD ONLY BE USED AFTER A FIND YIELDS NO RESULTS
    public UserDAO convertUser(User user){
        return new UserDAO(user.getDiscriminatedName(), user.getId());
    }

    public ServerDAO convertServer(Server server){
        return new ServerDAO(server.getName(), server.getId());
    }

    public MessageDAO convertMessage(Message message){
        return new MessageDAO(message.getId(), message.getContent(),
                message.getUserAuthor().get().getId(),
                message.getServer().get().getId());
    }

    public Lore convertLore(User user, Server server, Message message){
        return new Lore(message.getId(), server.getId(), user.getId());
    }
}
