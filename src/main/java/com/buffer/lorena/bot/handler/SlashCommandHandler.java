package com.buffer.lorena.bot.handler;

import com.buffer.lorena.bot.entity.Suggestion;
import com.buffer.lorena.bot.service.DiscordService;
import com.buffer.lorena.bot.service.LorenaService;
import com.buffer.lorena.commands.CommandService;
import com.buffer.lorena.commands.InvalidCommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Slash command handler.
 */
@Component
public class SlashCommandHandler {

    private final Logger logger = LogManager.getLogger(SlashCommandHandler.class);
    private final LorenaService lorenaService;
    private final DiscordApi api;
    private final List<CommandService> commandServices;

    /**
     * Instantiates a new Slash command handler.
     *
     * @param discordService the discord service
     * @param lorenaService  the lorena service
     */
    @Autowired
    public SlashCommandHandler(DiscordService discordService,
                               LorenaService lorenaService,
                               List<CommandService> commandServices){
        this.lorenaService = lorenaService;
        this.commandServices = commandServices;
        api = discordService.getDiscordApi();
        registerSlashCommands(api);
        registerSlashCommandsListener();
    }

    /**
     * Register slash commands.
     *
     * @param api the api
     */
    public void registerSlashCommands(DiscordApi api){
//        api.bulkOverwriteServerSlashCommands(api.getServerById(774734597816713216L).get(), new ArrayList<>());
//        api.bulkOverwriteGlobalSlashCommands(new ArrayList<>());
        commandServices.forEach(commandService -> commandService.registerCommand(api));
    }

    private void registerSlashCommandsListener(){
        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            commandServices.stream()
                    .filter(commandService -> commandService.applies(slashCommandInteraction.getCommandName()))
                    .findFirst().orElseGet(InvalidCommandService::new)
                    .commandListener(event, api, slashCommandInteraction);
        });
    }
}
