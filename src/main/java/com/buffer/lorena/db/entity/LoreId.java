package com.buffer.lorena.db.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The type Lore id.
 */
@Embeddable
public class LoreId implements Serializable {
    private long idServer;
    private long idUser;
    private long idMessage;

    /**
     * Instantiates a new Lore id.
     */
    public LoreId() {
    }

    /**
     * Instantiates a new Lore id.
     *
     * @param idServer  the id server
     * @param idUser    the id user
     * @param idMessage the id message
     */
    public LoreId(long idServer, long idUser, long idMessage) {
        this.idServer = idServer;
        this.idUser = idUser;
        this.idMessage = idMessage;
    }

    /**
     * Gets id server.
     *
     * @return the id server
     */
    public long getIdServer() {
        return idServer;
    }

    /**
     * Sets id server.
     *
     * @param idServer the id server
     */
    public void setIdServer(long idServer) {
        this.idServer = idServer;
    }

    /**
     * Gets id user.
     *
     * @return the id user
     */
    public long getIdUser() {
        return idUser;
    }

    /**
     * Sets id user.
     *
     * @param idUser the id user
     */
    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    /**
     * Gets id message.
     *
     * @return the id message
     */
    public long getIdMessage() {
        return idMessage;
    }

    /**
     * Sets id message.
     *
     * @param idMessage the id message
     */
    public void setIdMessage(long idMessage) {
        this.idMessage = idMessage;
    }
}
