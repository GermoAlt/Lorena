package com.buffer.lorena.bot.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * The type Lore id.
 */
@Embeddable
public class LoreId implements Serializable {
    @Column(name="ID_SERVER")
    private long idServer;
    @Column(name="ID_USER")
    private long idUser;
    @Column(name="ID_MESSAGE")
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
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoreId)) return false;
        LoreId loreId = (LoreId) o;
        return getIdServer() == loreId.getIdServer() && getIdUser() == loreId.getIdUser() && getIdMessage() == loreId.getIdMessage();
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(getIdServer(), getIdUser(), getIdMessage());
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
