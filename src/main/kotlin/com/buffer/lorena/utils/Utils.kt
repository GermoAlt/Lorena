package com.buffer.lorena.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

private val specials: List<Char> = listOf(
    '!', '\\', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', '|', '~', '`', '{', '}', '[', ']', ':', ';',
    '"', '\'', '<', '>', '?', '/', ',', '.'
)

inline val <T> Optional<T>.orNull: T? get() = if (this.isPresent) this.get() else null

fun String.removeTrailingSpecials(): String =
    if (this.lastOrNull() !in specials) this else this.substring(0 until (length-1)).removeTrailingSpecials()

fun tokenise(message: String, splitter: (String) -> List<String>): List<String> =
    message.split("""\s""".toRegex())
        .map { it.removeTrailingSpecials() }
        .flatMap { splitter(it) }
        .filterNot { it.isBlank() }

fun String.isNumeric(): Boolean {
    return this.toDoubleOrNull() != null
}

fun Double.digits(digits: Int): String =
    DecimalFormat("0.${"#".repeat(digits)}", DecimalFormatSymbols.getInstance(Locale.ROOT)).apply {
        roundingMode = RoundingMode.HALF_UP
    }.format(this)
