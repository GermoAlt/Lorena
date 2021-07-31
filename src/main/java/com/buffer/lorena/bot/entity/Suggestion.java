package com.buffer.lorena.bot.entity;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Suggestion {

    private String suggestion;
    private User author;
    private Server server;
    private DiscordApi api;

    public Suggestion() {
    }

    public Suggestion(String suggestion, User author, Server server, DiscordApi api) {
        this.suggestion = suggestion;
        this.author = author;
        this.server = server;
        this.api = api;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public DiscordApi getApi() {
        return api;
    }

    public void setApi(DiscordApi api) {
        this.api = api;
    }
}
