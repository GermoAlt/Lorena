package com.buffer.lorena.handler;

import com.buffer.lorena.bot.service.DiscordService
import com.buffer.lorena.bot.service.LorenaService
import com.buffer.lorena.commands.CommandService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.javacord.api.DiscordApi
import org.javacord.api.interaction.*
import org.springframework.stereotype.Component

/**
 * The type Slash command handler.
 */
@Component
class SlashCommandHandler (
    discordService: DiscordService,
    private val commandServices: List<CommandService>,
) {
    private val logger: Logger = LogManager.getLogger(SlashCommandHandler::class.java)
    private val api: DiscordApi = discordService.discordApi

    init {
        registerSlashCommands(api)
        registerSlashCommandsListener()
    }

    /**
     * Register slash commands.
     *
     * @param api the api
     */
    private fun registerSlashCommands(api: DiscordApi) {
//        api.bulkOverwriteServerSlashCommands(api.getServerById(774734597816713216L).get(), new ArrayList<>());
//        api.bulkOverwriteGlobalSlashCommands(new ArrayList<>());
        commandServices.forEach { commandService -> commandService.registerCommand(api) }
    }

    private fun registerSlashCommandsListener() {
        api.addSlashCommandCreateListener { event ->
            val slashCommandInteraction: SlashCommandInteraction = event.slashCommandInteraction
            commandServices.firstOrNull {
                it.applies(slashCommandInteraction.commandName)
            }?.commandListener(event, api, slashCommandInteraction) ?: slashCommandInteraction
                .createImmediateResponder().setContent("call the code monkey bc this shit dont work lmao").respond()
        }
    }
}
