package com.buffer.lorena.service

import com.buffer.lorena.utils.Units
import com.buffer.lorena.utils.Units.Companion.autoConverter
import com.buffer.lorena.utils.Units.Companion.corresponding
import com.buffer.lorena.utils.orNull
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.javacord.api.entity.message.MessageType
import org.javacord.api.event.message.MessageCreateEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriBuilder
import org.springframework.web.util.UriComponentsBuilder
import java.lang.Exception
import java.math.BigDecimal
import java.math.RoundingMode
import java.sql.Timestamp
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.measure.converter.ConversionException

@Service
class UnitConversionService(
    restTemplateBuilder: RestTemplateBuilder,
) {
    private val logger: Logger = LogManager.getLogger(UnitConversionService::class.java)

    private val specials: List<Char> = listOf(
        '!', '\\', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', '|', '~', '`', '{', '}', '[', ']', ':', ';',
        '"', '\'', '<', '>', '?', '/', ',', '.'
    )

    private val restTemplate = restTemplateBuilder.build()
    @Value("\${net.freecurrencyapi.token}")
    lateinit var currencyToken: String

    fun parseMessage(
        message: String,
        event: MessageCreateEvent,
    ) {
        if (event.messageAuthor.isBotUser || event.message.type.equals(MessageType.SLASH_COMMAND)) return
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

    fun convertCurrency(
        from: String,
        to: String,
        amount: String,
    ): String? {
        val url = "https://freecurrencyapi.net/api/v2/latest"
        val uri = UriComponentsBuilder.fromUriString(url)
            .queryParam("apikey", currencyToken)
            .queryParam("base_currency", from).build().toUri()
        try {
            val result = restTemplate.getForObject<CurrencyRespone>(uri)
            if (result.query.baseCurrency != from) return "Your from currency isn't supported"

            val conversionMultiplier = result.data[to] ?: return "Your to currency isn't supported"

            return "${amount.toDouble().digits(3)} $from is ${
                conversionMultiplier.multiply(amount.toBigDecimal()).toDouble().digits(3)
            } $to"
        } catch (e: HttpClientErrorException) {
            return if (e.statusCode.value() == 429) {
                "We have exceeded our conversion limit this month, which is like 50,000, so we should chill a bit..."
            } else {
                "I did something stupid when asking to have this converted, sorry..."
            }
        } catch (e: Exception) {
            logger.warn("Exception:", e)
            return "Unknown error happened, sorry..."
        }
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
    data class CurrencyRespone(
        val query: CurrencyQuery,
        val data: Map<String, BigDecimal>
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
    data class CurrencyQuery(
        val baseCurrency: String,
        val timestamp: Long,
    )
}