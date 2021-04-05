package com.buffer.lorena.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {

  @Id
  private long discIdMessage;
  private String messageText;
  private long idUser;
  private long idServ;
  private long idMessage;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;


  public long getDiscIdMessage() {
    return discIdMessage;
  }

  public void setDiscIdMessage(long discIdMessage) {
    this.discIdMessage = discIdMessage;
  }


  public String getMessageText() {
    return messageText;
  }

  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }


  public long getIdUser() {
    return idUser;
  }

  public void setIdUser(long idUser) {
    this.idUser = idUser;
  }


  public long getIdServ() {
    return idServ;
  }

  public void setIdServ(long idServ) {
    this.idServ = idServ;
  }


  public long getIdMessage() {
    return idMessage;
  }

  public void setIdMessage(long idMessage) {
    this.idMessage = idMessage;
  }


  public java.sql.Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(java.sql.Timestamp createdAt) {
    this.createdAt = createdAt;
  }


  public java.sql.Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(java.sql.Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

}
