package com.buffer.lorena.entity;


public class Lores {

  private long idLore;
  private long loreMessageId;
  private String loreStatus;
  private long discIdServer;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private long idUser;


  public long getIdLore() {
    return idLore;
  }

  public void setIdLore(long idLore) {
    this.idLore = idLore;
  }


  public long getLoreMessageId() {
    return loreMessageId;
  }

  public void setLoreMessageId(long loreMessageId) {
    this.loreMessageId = loreMessageId;
  }


  public String getLoreStatus() {
    return loreStatus;
  }

  public void setLoreStatus(String loreStatus) {
    this.loreStatus = loreStatus;
  }


  public long getDiscIdServer() {
    return discIdServer;
  }

  public void setDiscIdServer(long discIdServer) {
    this.discIdServer = discIdServer;
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


  public long getIdUser() {
    return idUser;
  }

  public void setIdUser(long idUser) {
    this.idUser = idUser;
  }

}
