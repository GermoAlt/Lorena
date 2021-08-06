package com.buffer.lorena.commands

import com.buffer.lorena.service.UnitConversionService
import com.buffer.lorena.utils.Units
import com.buffer.lorena.utils.orNull
import org.javacord.api.DiscordApi
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.interaction.SlashCommand
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UnitConversionCommandService(
    private val unitConversionService: UnitConversionService
): CommandService {
    private val logger: Logger = LoggerFactory.getLogger(UnitConversionCommandService::class.java)

    override val command: String
        get() = "convert"
    override val options: List<SlashCommandOption>
        get() = listOf(
            SlashCommandOption.create(
                SlashCommandOptionType.STRING,
                "amount",
                "The amount to convert",
                true,
            ), SlashCommandOption.create(
                SlashCommandOptionType.STRING,
                "from",
                "The unit to convert from",
                true,
            ), SlashCommandOption.create(
                SlashCommandOptionType.STRING,
                "to",
                "The unit to convert to",
                true,
            )
        )

    override fun applies(c: String): Boolean = c.equals(command, true)

    override fun commandListener(
        event: SlashCommandCreateEvent,
        api: DiscordApi,
        slashCommandInteraction: SlashCommandInteraction,
    ) {
        logger.info(event.interaction.toString())
        val fromAmount = slashCommandInteraction.getOptionByName(options[0].name).orNull?.stringValue?.orNull
        val fromUnit = slashCommandInteraction.getOptionByName(options[1].name).orNull?.stringValue?.orNull
        val toUnit = slashCommandInteraction.getOptionByName(options[2].name).orNull?.stringValue?.orNull
        if (fromAmount == null || fromUnit == null || toUnit == null) {
            slashCommandInteraction.createImmediateResponder().setContent("One of the arguments are invalid").respond()
            return
        }
        val fromUnits = Units.matchAll(fromUnit)
        val toUnits = Units.matchAll(toUnit)
        if (fromUnits == null || toUnits == null) {
            slashCommandInteraction.createImmediateResponder().setContent("One of those units are unknown").respond()
        } else {
            val response = unitConversionService.convert(fromUnits, toUnits, fromAmount)
            if (response == null) {
                slashCommandInteraction.createImmediateResponder().setContent("The amount is not a valid number").respond()
            } else {
                slashCommandInteraction.createImmediateResponder().setContent(response).respond()
            }

        }
    }

    override fun registerCommand(api: DiscordApi) {
        SlashCommand.with(
            command, "Convert from and to units of measurement",
            options,
        ).createGlobal(api).join()
    }
}