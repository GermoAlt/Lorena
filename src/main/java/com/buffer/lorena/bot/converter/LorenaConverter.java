package com.buffer.lorena.bot.converter;

import com.buffer.lorena.bot.repository.ServerRepository;
import com.buffer.lorena.bot.entity.*;
import org.javacord.api.entity.server.Server;
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


    /**
     * Instantiates a new Lorena converter.
     *
     * @param serverRepository the server repository
     */
    public LorenaConverter(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
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
            serverDAO.get().setName(server.getName());
            if (serverDAO.get().getCreatedAt() == null) serverDAO.get().setCreatedAt(Timestamp.from(Instant.now()));
            serverDAO.get().setUpdatedAt(Timestamp.from(Instant.now()));
        }
        return serverRepository.save(serverDAO.get());

    }
}
