package com.buffer.lorena.commands

import org.javacord.api.DiscordApi
import org.javacord.api.entity.server.Server
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption

class InvalidCommandService : CommandService {
    override val command: String
        get() = TODO("Not yet implemented")
    override val options: List<SlashCommandOption>
        get() = TODO("Not yet implemented")

    override fun applies(c: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun commandListener(
        event: SlashCommandCreateEvent,
        api: DiscordApi,
        slashCommandInteraction: SlashCommandInteraction
    ) {
        slashCommandInteraction.createImmediateResponder().setContent("call the code monkey bc this shit dont work lmao").respond()
    }

    override fun registerCommand(api: DiscordApi) {
        TODO("Not yet implemented")
    }
}