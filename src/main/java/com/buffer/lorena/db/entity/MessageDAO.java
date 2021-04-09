package com.buffer.lorena.db.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type Message dao.
 */
@Entity
@Table(name = "Message")
public class MessageDAO extends BaseEntity {

  @Id
  private long idMessage;
  private String messageText;
  private long idUser;
  private long idServ;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;

    /**
     * Instantiates a new Message dao.
     */
    public MessageDAO() {
  }

    /**
     * Instantiates a new Message dao.
     *
     * @param idMessage   the id message
     * @param messageText the message text
     * @param idUser      the id user
     * @param idServ      the id serv
     */
    public MessageDAO(long idMessage, String messageText, long idUser, long idServ) {
    this.idMessage = idMessage;
    this.messageText = messageText;
    this.idUser = idUser;
    this.idServ = idServ;
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


    /**
     * Gets message text.
     *
     * @return the message text
     */
    public String getMessageText() {
    return messageText;
  }

    /**
     * Sets message text.
     *
     * @param messageText the message text
     */
    public void setMessageText(String messageText) {
    this.messageText = messageText;
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
     * Gets id serv.
     *
     * @return the id serv
     */
    public long getIdServ() {
    return idServ;
  }

    /**
     * Sets id serv.
     *
     * @param idServ the id serv
     */
    public void setIdServ(long idServ) {
    this.idServ = idServ;
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
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(java.sql.Timestamp createdAt) {
    this.createdAt = createdAt;
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
    public void setUpdatedAt(java.sql.Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

}
