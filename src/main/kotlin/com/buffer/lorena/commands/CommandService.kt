package com.buffer.lorena.commands

import org.javacord.api.DiscordApi
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.springframework.beans.factory.annotation.Value

abstract class CommandService() {
    @Value("\${spring.profiles.active}")
    private var profile: String = ""

    abstract val command: String
    protected val actualCommand: String
        get() = "${if (profile == "dev") "dev-" else ""}$command"
    abstract val options: List<SlashCommandOption>

    fun applies(c: String): Boolean = c.equals(actualCommand, true)

    abstract fun commandListener(
        event: SlashCommandCreateEvent,
        api: DiscordApi,
        slashCommandInteraction: SlashCommandInteraction,
    )
    abstract fun registerCommand(
        api: DiscordApi,
    )
}