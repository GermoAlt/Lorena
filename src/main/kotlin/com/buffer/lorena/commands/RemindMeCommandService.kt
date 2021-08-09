package com.buffer.lorena.commands

import com.buffer.lorena.service.UnitConversionService
import com.buffer.lorena.utils.Units
import com.buffer.lorena.utils.orNull
import com.buffer.lorena.utils.tokenise
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.javacord.api.DiscordApi
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.interaction.SlashCommand
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Service

@Service
class RemindMeCommandService(
) : CommandService {
    private val logger: Logger = LogManager.getLogger(UnitConversionService::class.java)

    private val timeUnits: Array<Units> = arrayOf(
        Units.SECOND, Units.MINUTE, Units.HOUR, Units.DAY, Units.WEEK, Units.YEAR, Units.MONTH
    )
    private val timeUnitsNames = timeUnits.flatMap { it.ciNames.toSet() }.toSet()

    override val command: String
        get() = "remindme"
    override val options: List<SlashCommandOption>
        get() = listOf(
            SlashCommandOption.create(
                SlashCommandOptionType.STRING,
                "what",
                "What do you want to be reminded about",
                true,
            ),
            SlashCommandOption.create(
                SlashCommandOptionType.STRING,
                "when",
                "When do you want to be reminded about it",
                true,
            ),
            SlashCommandOption.create(
                SlashCommandOptionType.BOOLEAN,
                "dm",
                "Do you want to remind me in a DM?",
                false,
            )
        )

    override fun applies(c: String): Boolean = c.equals(command, true)

    override fun commandListener(
        event: SlashCommandCreateEvent,
        api: DiscordApi,
        slashCommandInteraction: SlashCommandInteraction
    ) {
        event.slashCommandInteraction.let { sci ->
            sci.respondLater().thenAccept { response ->
                // First try to see if it's doable with Units :D
                val message: String? = sci.firstOptionStringValue.orNull
                val timeframe = sci.secondOptionStringValue.orNull

                logger.info("We actually have 2 required parameters: {}, {}", message, timeframe)

                if (timeframe == null || message == null) {
                    response.setContent("Required parameters not provided").update()
                    return@thenAccept
                }
                val tokens = tokeniseMessage(timeframe)
                val detected = Units.detect(tokens, timeUnitsNames) { first, second ->
                    Units.match(first, second, timeUnits)
                }
                if (detected.isNotEmpty()) {
                    // We go by this time
                    response.setContent("The time is set to: $detected").update()
                } else {
                    // Continue parsing attempts
                    response.setContent("I don't work yet, doofus").update()
                }
            }
        }
    }

    override fun registerCommand(api: DiscordApi) {
        SlashCommand.with(
            command, "Remind me after some time",
            options,
        ).createGlobal(api).join()
    }

    private fun tokeniseMessage(message: String): List<String> =
        tokenise(message) { token -> Units.splitToken(token, timeUnitsNames) }
}