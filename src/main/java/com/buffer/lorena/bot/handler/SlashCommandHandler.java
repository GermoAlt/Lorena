package com.buffer.lorena.bot.handler;

import com.buffer.lorena.bot.entity.Suggestion;
import com.buffer.lorena.bot.service.DiscordService;
import com.buffer.lorena.bot.service.LorenaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Slash command handler.
 */
@Component
public class SlashCommandHandler {

    private final Logger logger = LogManager.getLogger(SlashCommandHandler.class);
    private final LorenaService lorenaService;
    private final DiscordApi api;
    private static final String SUGGESTION_COMMAND_NAME = "suggest";
    private static final String SUGGESTION_OPTION_NAME = "suggestion";

    /**
     * Instantiates a new Slash command handler.
     *
     * @param discordService the discord service
     * @param lorenaService  the lorena service
     */
    @Autowired
    public SlashCommandHandler(DiscordService discordService, LorenaService lorenaService){
        this.lorenaService = lorenaService;
        api = discordService.getDiscordApi();
        registerSlashCommands(api.getServerById(774734597816713216L).get());
        registerSlashCommandsListener();
    }

    /**
     * Register slash commands.
     *
     * @param server the server
     */
    public void registerSlashCommands(Server server){
        SlashCommand.with(SUGGESTION_COMMAND_NAME, "Create a suggestion",
            List.of(
                    SlashCommandOption.create(SlashCommandOptionType.STRING, SUGGESTION_OPTION_NAME, "The suggestion", true)
        ))
        .createForServer(server)
        .join();

    }

    private void registerSlashCommandsListener(){
        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            switch (slashCommandInteraction.getCommandName()){
                case SUGGESTION_COMMAND_NAME:
                    logger.info(event.getInteraction());
                    slashCommandInteraction.createImmediateResponder().setContent("Suggestion noted.").respond();
                    Suggestion suggestion = new Suggestion(slashCommandInteraction.getOptionByName(SUGGESTION_OPTION_NAME).get().getStringValue().get(),
                            slashCommandInteraction.getUser(),
                            slashCommandInteraction.getServer().get(),
                            api);
                    this.lorenaService.handleSuggestion(suggestion);
                    break;
                default:
                    slashCommandInteraction.createImmediateResponder().setContent("call the code monkey bc this shit dont work lmao").respond();
                    break;
            }
        });
    }
}
