package com.buffer.lorena.utils

import java.util.*
import javax.measure.converter.UnitConverter
import javax.measure.quantity.Area
import javax.measure.unit.NonSI
import javax.measure.unit.ProductUnit
import javax.measure.unit.SI
import javax.measure.unit.SI.*
import javax.measure.unit.Unit as JUnit

enum class Units(val unit: JUnit<*>, val printedName: String, val ciNames: List<String>, vararg val csNames: String) {
    // Base units
    KELVIN(SI.KELVIN, "K", listOf("kelvin"), "K"),
    KILOGRAM(SI.KILOGRAM, "kg", listOf("kg", "kilogram", "kgs", "kilograms")),
    METRE(SI.METRE, "m", listOf("metre", "meter", "meters", "metres", "m")),
    SECOND(SI.SECOND, "sec", listOf("second", "seconds", "s")),

    // Derived units
    GRAM(SI.GRAM, "g", listOf("gram", "grams", "g")),
    CELSIUS(SI.CELSIUS, "°C", listOf("celsius", "°c", "°"), "C"),
    // Double
    SQUARE_METRE(SI.SQUARE_METRE, "m²", listOf("m²", "m2", "square meter", "square meters")),
    KILOMETRE(SI.KILOMETRE, "km", listOf("km", "kilometre", "kilometres", "kilometer", "kilometers")),
    CENTIMETRE(SI.CENTIMETRE, "cm", listOf("cm", "centimetre", "centimetres", "centimeter", "centimeters")),
    MILLIMETRE(SI.MILLIMETRE, "mm", listOf("mm", "millimetre", "millimeters", "millimeter", "millimeters")),

    // Non SI
    FOOT(NonSI.FOOT, "ft", listOf("ft", "foot", "feet")),
    // Double
    SQUARE_FOOT(ProductUnit<Area>(NonSI.FOOT.times(NonSI.FOOT)), "ft²", listOf("ft2", "ft²", "sq ft", "sq. ft", "square feet")),
    YARD(NonSI.YARD, "yd", listOf("yard", "yards", "yd")),
    INCH(NonSI.INCH, "in", listOf("inch", "inches", "in", """"""")),
    MILE(NonSI.MILE, "mi", listOf("mile", "miles", "mi")),

    MINUTE(NonSI.MINUTE, "min", listOf("min", "minute", "minutes")),
    HOUR(NonSI.HOUR, "hr", listOf("hours", "hour", "hr", "h")),
    DAY(NonSI.DAY, "d", listOf("days", "day", "d")),
    WEEK(NonSI.WEEK, "w", listOf("weeks", "week", "w")),
    YEAR(NonSI.YEAR, "yr", listOf("year", "years", "yr")),
    MONTH(NonSI.MONTH, "mths", listOf("month", "months", "mo")),

    POUND(NonSI.POUND, "lbs", listOf("pound", "pounds", "lb", "lbs")),
    OUNCE(NonSI.OUNCE, "oz", listOf("ounce", "ounces", "oz")),
    TON(NonSI.METRIC_TON, "t", listOf("ton", "tonne", "tons", "tonnes", "t")),
    // Double
    US_TON(NonSI.TON_US, "us t", listOf("us ton", "us tons", "imperial ton", "imperial tons")),

    FAHRENHEIT(NonSI.FAHRENHEIT, "°F", listOf("°f", "fahrenheit"), "F"),

    KMH(NonSI.KILOMETRES_PER_HOUR, "km/h", listOf("km/h", "kmh", "kph")),
    MPH(NonSI.MILES_PER_HOUR, "mph", listOf("mph")),

    LITRE(NonSI.LITRE, "l", listOf("l", "litre", "litres", "liter", "liters")),
    MILLILITRE(MILLI(NonSI.LITRE), "ml", listOf("ml", "millilitre", "milliliter", "millilitres", "milliliters")),
    CENTILITRE(CENTI(NonSI.LITRE), "cl", listOf("cl", "centilitre", "centilitres", "centiliter", "centiliters")),
    DECILITRE(DECI(NonSI.LITRE), "dl", listOf("dl", "decilitre", "decilitres", "deciliter", "deciliters")),
    // Double
    LIQUID_OUNCE(NonSI.OUNCE_LIQUID_US, "fl. oz", listOf("liquid ounce", "liquid ounces", "fl oz", "fl. oz")),
    US_GALLON(NonSI.GALLON_LIQUID_US, "gal", listOf("gallon", "gallons", "gal")),
    CUP(NonSI.LITRE.times(0.236588), "cups", listOf("cup", "cups")),

    // Jokes
    GIRLS(CUP.unit.divide(2), "girls", listOf("girls", "girl"))
    ;

    companion object {
        private val AUTO_CONVERSION: Array<Units> = arrayOf(
            KILOGRAM,
            METRE, KILOMETRE, CENTIMETRE, MILLIMETRE,
            CELSIUS,
            SQUARE_METRE,
            FOOT,
            SQUARE_FOOT,
            YARD,
            INCH,
            MILE,
            POUND,
            OUNCE,
            TON,
            FAHRENHEIT,
            KMH,
            MPH,
            LITRE, MILLILITRE, CENTILITRE, DECILITRE,
            LIQUID_OUNCE,
            US_GALLON,
            CUP,
        )
        private val AUTO_CONVERTERS: Map<Units, Units> = mapOf(
            KILOGRAM to POUND,
            METRE to FOOT,
            KILOMETRE to MILE,
            CENTIMETRE to INCH,
            MILLIMETRE to INCH,
            CELSIUS to FAHRENHEIT,
            SQUARE_METRE to SQUARE_FOOT,
            FOOT to METRE,
            SQUARE_FOOT to SQUARE_METRE,
            YARD to METRE,
            INCH to CENTIMETRE,
            MILE to KILOMETRE,
            POUND to KILOGRAM,
            OUNCE to KILOGRAM,
            TON to POUND,
            FAHRENHEIT to CELSIUS,
            KMH to MPH,
            MPH to KMH,
            LITRE to US_GALLON,
            MILLILITRE to LIQUID_OUNCE,
            CENTILITRE to LIQUID_OUNCE,
            DECILITRE to LIQUID_OUNCE,
            LIQUID_OUNCE to MILLILITRE,
            US_GALLON to LITRE,
            CUP to MILLILITRE,
        )

        val AUTO_CONVERSION_NAMES: Set<String> = AUTO_CONVERSION.flatMap { it.ciNames + it.csNames }.toSet()
        val ALL_NAMES: Set<String> = values().flatMap { it.ciNames + it.csNames }.toSet()

        fun Units.corresponding(): Units? = AUTO_CONVERTERS[this]

        fun Pair<Units, Units>.autoConverter(): UnitConverter? = AUTO_CONVERTERS[this.first]?.let {
            this.first.unit.getConverterTo(it.unit)
        }

        fun splitTokenAuto(
            token: String,
        ): List<String> = splitToken(token, AUTO_CONVERSION_NAMES)

        fun matchAuto(
            first: String,
            second: String? = null,
        ): Units? = match(first, second, AUTO_CONVERSION)

        fun matchAll(
            first: String,
            second: String? = null,
        ): Units? = match(first, second, values())

        private fun splitToken(
            token: String,
            collection: Set<String>
        ): List<String> {
            return collection.find { token.contains("$it\\b".toRegex()) }?.let {
                val first = token.substring(0 until token.length - it.length)
                if (first.toDoubleOrNull() != null) listOf(first, it) else listOf(token)
            } ?: listOf(token)
        }

        private fun match(
            first: String,
            second: String?,
            collection: Array<Units>
        ): Units? {
            // We will do three runs to check for matches
            return collection.find {
                // First run is checking for case sensitive matches
                first in it.csNames
            } ?: collection.find {
                // Then we check insensitive matches
                first.lowercase(Locale.ROOT) in it.ciNames
            } ?: if (second != null ) collection.find {
                // Then we check for double matches
                "$first $second".lowercase(Locale.ROOT) in it.ciNames
            } else null
        }
    }
}