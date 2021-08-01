package com.buffer.lorena.service

import com.buffer.lorena.utils.Units
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.javacord.api.event.message.MessageCreateEvent
import org.springframework.stereotype.Service

@Service
class UnitConversionService {
    private val logger: Logger = LogManager.getLogger(UnitConversionService::class.java)

    fun parseMessage(
        message: String,
        event: MessageCreateEvent,
    ) {
        // First we check if the message contains any of the names
        if (Units.AUTO_CONVERSION_NAMES.none { message.contains(it) }) return

        logger.info("Message contains a unit name")

        val tokens = message.split("""\s""").flatMap { token ->
            // We can ignore doubles when having no space
            if (Units.AUTO_CONVERSION_NAMES. { token.endsWith(it) }) {
                listOf()
            } else {
                listOf(token)
            }
        }

    }
}