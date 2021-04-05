package com.buffer.lorena.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserDAO {
  @Id
  @GeneratedValue
  private long idUser;
  private String userName;
  private long discIdUser;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;

  public UserDAO(String userName, long discIdUser) {
    this.userName = userName;
    this.discIdUser = discIdUser;
  }

  public UserDAO() {
  }

  public long getIdUser() {
    return idUser;
  }

  public void setIdUser(long idUser) {
    this.idUser = idUser;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public long getDiscIdUser() {
    return discIdUser;
  }

  public void setDiscIdUser(long discIdUser) {
    this.discIdUser = discIdUser;
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
