package com.buffer.lorena.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DiscordService {
    private final Logger logger = LogManager.getLogger(DiscordService.class);

    @Value("${lorena.token}")
    String token;
    private DiscordApi discordApi;

    @PostConstruct
    public void login(){
        logger.info(token);
        this.discordApi = new DiscordApiBuilder().setToken(token)
                .setAllNonPrivilegedIntents()
                .login().join();
    }

    /**
     * Gets discordApi.
     *
     * @return Value of discordApi.
     */
    public DiscordApi getDiscordApi() {
        return discordApi;
    }
}
