package com.buffer.lorena.commands

import org.javacord.api.DiscordApi
import org.javacord.api.entity.server.Server
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption

interface CommandService {
    val command: String
    val options: List<SlashCommandOption>

    fun applies(c: String): Boolean
    fun commandListener(
        event: SlashCommandCreateEvent,
        api: DiscordApi,
        slashCommandInteraction: SlashCommandInteraction,
    )
    fun registerCommand(
        server: Server,
    )
}