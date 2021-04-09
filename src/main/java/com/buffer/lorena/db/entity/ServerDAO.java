package com.buffer.lorena.db.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type Server dao.
 */
@Entity
@Table(name = "Server")
public class ServerDAO extends BaseEntity {

  @Id
  private long idServer;
  private String name;
  private double userVoteThreshold;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;

    /**
     * Instantiates a new Server dao.
     */
    public ServerDAO() {
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
    public double getUserVoteThreshold() {
    return userVoteThreshold;
  }

    /**
     * Sets user vote threshold.
     *
     * @param userVoteThreshold the user vote threshold
     */
    public void setUserVoteThreshold(double userVoteThreshold) {
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
