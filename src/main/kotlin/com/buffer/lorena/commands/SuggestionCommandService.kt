package com.buffer.lorena.commands

import com.buffer.lorena.bot.entity.Suggestion
import com.buffer.lorena.bot.handler.SlashCommandHandler
import com.buffer.lorena.bot.service.LorenaService
import com.buffer.lorena.utils.orNull
import org.javacord.api.DiscordApi
import org.javacord.api.entity.server.Server
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.interaction.SlashCommand
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SuggestionCommandService(
    private val lorenaService: LorenaService
) : CommandService {
    private val logger: Logger = LoggerFactory.getLogger(SuggestionCommandService::class.java)

    override val command: String
        get() = "suggest"
    override val options: List<SlashCommandOption>
        get() = listOf(
            SlashCommandOption.create(
                SlashCommandOptionType.STRING,
                "suggestion",
                "The suggestion",
                true
            )
        )

    override fun applies(c: String): Boolean = c.equals(command, true)

    override fun commandListener(
        event: SlashCommandCreateEvent,
        api: DiscordApi,
        slashCommandInteraction: SlashCommandInteraction,
    ) {
        logger.info(event.interaction.toString())
        slashCommandInteraction.createImmediateResponder().setContent("Suggestion noted.").respond()
        val option: String = slashCommandInteraction.getOptionByName(options[0].name).orNull?.stringValue?.orNull ?: return
        val server: Server = slashCommandInteraction.server.orNull ?: return
        val suggestion = Suggestion(option, slashCommandInteraction.user, server, api)
        lorenaService.handleSuggestion(suggestion)
    }

    override fun registerCommand(server: Server) {
        SlashCommand.with(
            command, "Create a suggestion",
            options,
        ).createForServer(server).join()
    }
}