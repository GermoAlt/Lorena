package com.buffer.lorena.service

import com.buffer.lorena.utils.Units
import com.buffer.lorena.utils.Units.Companion.autoConverter
import com.buffer.lorena.utils.Units.Companion.corresponding
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.javacord.api.event.message.MessageCreateEvent
import org.springframework.stereotype.Service
import javax.measure.converter.ConversionException

@Service
class UnitConversionService {
    private val logger: Logger = LogManager.getLogger(UnitConversionService::class.java)

    fun parseMessage(
        message: String,
        event: MessageCreateEvent,
    ) {
        if (event.messageAuthor.isBotUser) return
        // First we check if the message contains any of the names
        if (Units.AUTO_CONVERSION_NAMES.none { message.contains("$it\\b".toRegex()) }) return

        logger.info("Message contains a unit name")

        // Split tokens if they end on a conversion unit
        val tokens = message.split("""\s""".toRegex()).flatMap {
            Units.splitTokenAuto(it)
        }.filterNot { it.isBlank() }

        val forConversion = tokens.mapIndexedNotNull { index, token ->
            when {
                index == 0 -> null
                Units.AUTO_CONVERSION_NAMES.contains(token) && tokens[index-1].isNumeric() ->
                    tokens[index-1].toDouble() to Units.matchAuto(token)
                // Liquid ounce fix
                index > 1 && Units.AUTO_CONVERSION_NAMES.contains("${tokens[index-1]} $token") &&
                        tokens[index-2].isNumeric() ->
                    tokens[index-2].toDouble() to Units.matchAuto(tokens[index-1], token)
                else -> null
            }
        }

        val converted = forConversion.mapNotNull { (value, unit) ->
            unit?.let { u ->
                u.corresponding()?.let { c ->
                    (u to c).autoConverter()?.let { converter ->
                        val convertedValue: Double? = try {
                            converter.convert(value.toDouble())
                        } catch (e: ConversionException) {
                            logger.warn("ConversionException: {} to {}", u, c, e)
                            null
                        }
                        convertedValue?.let { "$value ${u.printedName} is $it ${c.printedName}" }
                    }
                }
            }
        }

        val msg = converted.joinToString(", ")
        event.channel.sendMessage(msg)
    }

    private fun String.isNumeric(): Boolean {
        return this.toDoubleOrNull() != null
    }
}