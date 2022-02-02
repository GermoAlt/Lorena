package com.buffer.lorena.bot.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * The type Lore.
 */
@Entity
@Table(name = "Lore")
public class Lore {

  @EmbeddedId
  private LoreId loreId;
  @Column(name = "createdAt")
  private java.sql.Timestamp createdAt;
  @Column(name = "updatedAt")
  private java.sql.Timestamp updatedAt;
  @Column(name = "active")
  private boolean active;

    /**
     * Instantiates a new Lore.
     */
    public Lore() {
  }

    /**
     * Instantiates a new Lore.
     *
     * @param loreId the lore id
     */
    public Lore(LoreId loreId) {
    this.loreId = loreId;
    this.createdAt = Timestamp.from(Instant.now());
    this.active = true;
  }

    /**
     * Instantiates a new Lore.
     *
     * @param idServer  the id server
     * @param idUser    the id user
     * @param idMessage the id message
     */
    public Lore(long idServer, long idUser, long idMessage) {
    this.loreId = new LoreId(idServer, idUser, idMessage);
    this.createdAt = Timestamp.from(Instant.now());
  }

    /**
     * Gets id server.
     *
     * @return the id server
     */
    public long getIdServer() {
    return this.loreId.getIdServer();
  }

    /**
     * Gets id user.
     *
     * @return the id user
     */
    public long getIdUser() {
    return this.loreId.getIdUser();
  }

    /**
     * Gets id message.
     *
     * @return the id message
     */
    public long getIdMessage() {
    return this.loreId.getIdMessage();
  }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public java.sql.Timestamp getCreatedAt() {
    return createdAt;
  }

    /**
     * Gets updated at.
     *
     * @return the updated at
     */
    public java.sql.Timestamp getUpdatedAt() {
    return updatedAt;
  }

    /**
     * Sets updated at.
     *
     * @param updatedAt the updated at
     */
    public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

    /**
     * Gets lore id.
     *
     * @return the lore id
     */
    public LoreId getLoreId() {
    return loreId;
  }

    /**
     * Sets lore id.
     *
     * @param loreId the lore id
     */
    public void setLoreId(LoreId loreId) {
    this.loreId = loreId;
  }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }
}
