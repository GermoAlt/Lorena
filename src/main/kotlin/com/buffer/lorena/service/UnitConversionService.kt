package com.buffer.lorena.service

import com.buffer.lorena.utils.Units
import com.buffer.lorena.utils.Units.Companion.autoConverter
import com.buffer.lorena.utils.Units.Companion.corresponding
import com.buffer.lorena.utils.Units.Companion.detectAuto
import com.buffer.lorena.utils.digits
import com.buffer.lorena.utils.isNumeric
import com.buffer.lorena.utils.tokenise
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.javacord.api.entity.message.MessageType
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
        if (event.messageAuthor.isBotUser || event.message.type.equals(MessageType.SLASH_COMMAND)) return
        // First we check if the message contains any of the names

        // Split tokens if they end on a conversion unit
        val tokens = tokeniseMessage(message)

        val forConversion = detectAuto(tokens)

        val converted = forConversion.mapNotNull { (value, unit) ->
            unit?.let { u ->
                u.corresponding()?.let { c ->
                    (u to c).autoConverter()?.let { converter ->
                        val convertedValue: Double? = try {
                            converter.convert(value)
                        } catch (e: ConversionException) {
                            logger.warn("ConversionException: {} to {}", u, c, e)
                            null
                        }
                        convertedValue?.let { "${value.digits(3)} ${u.printedName} is ${it.digits(3)} ${c.printedName}" }
                    }
                }
            }
        }

        val msg = converted.joinToString(", ")
        event.channel.sendMessage(msg)
    }

    fun convert(
        fromUnit: Units,
        toUnit: Units,
        amount: String,
    ): String? {
        if (!fromUnit.unit.isCompatible(toUnit.unit)) return "These units do not match"
        val unitConverter = fromUnit.unit.getConverterTo(toUnit.unit)
        if (!amount.isNumeric()) return null

        val converted = unitConverter.convert(amount.toDouble())
        return "${amount.toDouble().digits(3)} ${fromUnit.printedName} is ${converted.digits(3)} ${toUnit.printedName}"
    }

    private fun tokeniseMessage(message: String): List<String> = tokenise(message, Units.Companion::splitTokenAuto)
}