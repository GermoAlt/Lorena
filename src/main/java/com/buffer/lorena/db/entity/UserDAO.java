package com.buffer.lorena.db.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type User dao.
 */
@Entity
@Table(name = "User")
public class UserDAO extends BaseEntity {

  @Id
  private long idUser;
  private String name;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;

    /**
     * Instantiates a new User dao.
     */
    public UserDAO() {
  }

    /**
     * Instantiates a new User dao.
     *
     * @param idUser the id user
     * @param name   the name
     */
    public UserDAO(long idUser, String name) {
    this.idUser = idUser;
    this.name = name;
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
