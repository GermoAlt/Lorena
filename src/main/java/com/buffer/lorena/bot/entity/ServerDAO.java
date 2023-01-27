package com.buffer.lorena.bot.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * The type Server dao.
 */
@Document("server")
public class ServerDAO {

  @Id
  private long idServer;
  private String name;
  private Integer userVoteThreshold;
  private Long loreChannel;
  private Long suggestionChannel;
  private Date createdAt;
  private Date updatedAt;

  /**
   * Instantiates a new Server dao.
   */
  public ServerDAO() {
    this.createdAt = new Date();
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
    this.createdAt = new Date();
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
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets created at.
   *
   * @param createdAt the created at
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }


  /**
   * Gets updated at.
   *
   * @return the updated at
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets updated at.
   *
   * @param updatedAt the updated at
   */
  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Gets lore channel.
   *
   * @return the lore channel
   */
  public Long getLoreChannel() {
    return loreChannel;
  }

  /**
   * Sets lore channel.
   *
   * @param loreChannel the lore channel
   */
  public void setLoreChannel(Long loreChannel) {
    this.loreChannel = loreChannel;
  }

  /**
   * Gets suggestion channel.
   *
   * @return the suggestion channel
   */
  public Long getSuggestionChannel() {
    return suggestionChannel;
  }

  /**
   * Sets suggestion channel.
   *
   * @param suggestionChannel the suggestion channel
   */
  public void setSuggestionChannel(Long suggestionChannel) {
    this.suggestionChannel = suggestionChannel;
  }
}
