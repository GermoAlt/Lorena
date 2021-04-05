package com.buffer.lorena.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "servers")
public class Server {
  @Id
  private long idServer;
  private String servName;
  private long discIdServer;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private double userVoteThreshold;


  public long getIdServer() {
    return idServer;
  }

  public void setIdServer(long idServer) {
    this.idServer = idServer;
  }


  public String getServName() {
    return servName;
  }

  public void setServName(String servName) {
    this.servName = servName;
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


  public double getUserVoteThreshold() {
    return userVoteThreshold;
  }

  public void setUserVoteThreshold(double userVoteThreshold) {
    this.userVoteThreshold = userVoteThreshold;
  }

}
