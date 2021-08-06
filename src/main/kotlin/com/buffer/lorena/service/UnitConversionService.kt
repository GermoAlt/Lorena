package com.buffer.lorena.service

import com.buffer.lorena.utils.Units
import com.buffer.lorena.utils.Units.Companion.autoConverter
import com.buffer.lorena.utils.Units.Companion.corresponding
import com.buffer.lorena.utils.orNull
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.javacord.api.event.message.MessageCreateEvent
import org.springframework.stereotype.Service
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.measure.converter.ConversionException

@Service
class UnitConversionService {
    private val logger: Logger = LogManager.getLogger(UnitConversionService::class.java)

    private val specials: List<Char> = listOf(
        '!', '\\', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', '|', '~', '`', '{', '}', '[', ']', ':', ';',
        '"', '\'', '<', '>', '?', '/', ',', '.'
    )

    fun parseMessage(
        message: String,
        event: MessageCreateEvent,
    ) {
        // TODO Noodle this is so annoying, but idk how to fix it, the bot response to a slash command is not a bot user
        if (event.messageAuthor.isBotUser || event.messageAuthor.name.lowercase().contains("lorena")) return
        // First we check if the message contains any of the names

        // Split tokens if they end on a conversion unit
        val tokens = message.split("""\s""".toRegex())
            .map { it.removeTrailingSpecials() }
            .flatMap { Units.splitTokenAuto(it) }
            .filterNot { it.isBlank() }

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

    private fun String.isNumeric(): Boolean {
        return this.toDoubleOrNull() != null
    }

    private fun Double.digits(digits: Int): String =
        DecimalFormat("0.${"#".repeat(digits)}", DecimalFormatSymbols.getInstance(Locale.ROOT)).apply {
                roundingMode = RoundingMode.HALF_UP
        }.format(this)

    private fun String.removeTrailingSpecials(): String =
        if (this.lastOrNull() !in specials) this else this.substring(0 until (length-1)).removeTrailingSpecials()
}