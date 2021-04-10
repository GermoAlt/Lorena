package com.buffer.lorena.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * The type Server dao.
 */
@Entity
@Table(name = "Server")
public class ServerDAO {

  @Id
  private long idServer;
  private String name;
  private Integer userVoteThreshold;
  @Column(name = "createdAt")
  private java.sql.Timestamp createdAt;
  @Column(name = "updatedAt")
  private java.sql.Timestamp updatedAt;

  /**
   * Instantiates a new Server dao.
   */
  public ServerDAO() {
    this.createdAt = Timestamp.from(Instant.now());
  }

  /**
   * Instantiates a new Server dao.
   *
   * @param idServer the id server
   * @param name     the name
   */
  public ServerDAO(long idServer, String name) {
    this.idServer = idServer;
    this.name = name;
    this.createdAt = Timestamp.from(Instant.now());
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
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }


  /**
   * Gets user vote threshold.
   *
   * @return the user vote threshold
   */
  public Integer getUserVoteThreshold() {
    return userVoteThreshold;
  }

  /**
   * Sets user vote threshold.
   *
   * @param userVoteThreshold the user vote threshold
   */
  public void setUserVoteThreshold(Integer userVoteThreshold) {
    this.userVoteThreshold = userVoteThreshold;
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
