package com.buffer.lorena.bot.entity;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

/**
 * The type Suggestion.
 */
public class Suggestion {

    private String suggestion;
    private User author;
    private Server server;
    private DiscordApi api;

    /**
     * Instantiates a new Suggestion.
     */
    public Suggestion() {
    }

    /**
     * Instantiates a new Suggestion.
     *
     * @param suggestion the suggestion
     * @param author     the author
     * @param server     the server
     * @param api        the api
     */
    public Suggestion(String suggestion, User author, Server server, DiscordApi api) {
        this.suggestion = suggestion;
        this.author = author;
        this.server = server;
        this.api = api;
    }

    /**
     * Gets suggestion.
     *
     * @return the suggestion
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     * Sets suggestion.
     *
     * @param suggestion the suggestion
     */
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Gets server.
     *
     * @return the server
     */
    public Server getServer() {
        return server;
    }

    /**
     * Sets server.
     *
     * @param server the server
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * Gets api.
     *
     * @return the api
     */
    public DiscordApi getApi() {
        return api;
    }

    /**
     * Sets api.
     *
     * @param api the api
     */
    public void setApi(DiscordApi api) {
        this.api = api;
    }
}
