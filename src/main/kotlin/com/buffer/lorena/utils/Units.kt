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
    KELVIN(SI.KELVIN, "kelvin", listOf("kelvin"), "K"),
    KILOGRAM(SI.KILOGRAM, "kilograms", listOf("kg, kilogram", "kgs", "kilograms")),
    METRE(SI.METRE, "metres", listOf("metre", "meter", "meters", "metres", "m")),
    SECOND(SI.SECOND, "seconds", listOf("second", "seconds", "s")),

    // Derived units
    GRAM(SI.GRAM, "grams", listOf("gram", "grams", "g")),
    CELSIUS(SI.CELSIUS, "degrees Celsius", listOf("celsius", "°c", "°"), "C"),
    // Double
    SQUARE_METRE(SI.SQUARE_METRE, "square metres", listOf("m²", "m2", "square meter", "square meters")),
    KILOMETRE(SI.KILOMETRE, "kilometres", listOf("km", "kilometre", "kilometres", "kilometer", "kilometers")),
    CENTIMETRE(SI.CENTIMETRE, "centimetres", listOf("cm", "centimetre", "centimetres", "centimeter", "centimeters")),
    MILLIMETRE(SI.MILLIMETRE, "millimetres", listOf("mm", "millimetre", "millimeters", "millimeter", "millimeters")),

    // Non SI
    FOOT(NonSI.FOOT, "feet", listOf("ft", "foot", "feet")),
    // Double
    SQUARE_FOOT(ProductUnit<Area>(NonSI.FOOT.times(NonSI.FOOT)), "square feet", listOf("ft2", "ft²", "sq ft", "sq. ft", "square feet")),
    YARD(NonSI.YARD, "yards", listOf("yard", "yards", "yd")),
    INCH(NonSI.INCH, "inches", listOf("inch", "inches", "in", """"""")),
    MILE(NonSI.MILE, "miles", listOf("mile", "miles", "mi")),

    MINUTE(NonSI.MINUTE, "minutes", listOf("min", "minute", "minutes")),
    HOUR(NonSI.HOUR, "hours", listOf("hours", "hour", "hr", "h")),
    DAY(NonSI.DAY, "days", listOf("days", "day", "d")),
    WEEK(NonSI.WEEK, "weeks", listOf("weeks", "week", "w")),
    YEAR(NonSI.YEAR, "years", listOf("year", "years", "yr")),
    MONTH(NonSI.MONTH, "months", listOf("month", "months", "mo")),

    POUND(NonSI.POUND, "pounds", listOf("pound", "pounds", "lb", "lbs")),
    OUNCE(NonSI.OUNCE, "ounces", listOf("ounce", "ounces", "oz")),
    TON(NonSI.METRIC_TON, "tonnes", listOf("ton", "tonne", "tons", "tonnes", "t")),
    // Double
    US_TON(NonSI.TON_US, "US tons", listOf("us ton", "us tons", "imperial ton", "imperial tons")),

    FAHRENHEIT(NonSI.FAHRENHEIT, "degrees Fahrenheit", listOf("°F", "fahrenheit"), "F"),

    KMH(NonSI.KILOMETRES_PER_HOUR, "kilometres per hour", listOf("km/h", "kmh", "kph")),
    MPH(NonSI.MILES_PER_HOUR, "miles per hour", listOf("mph")),

    LITRE(NonSI.LITRE, "litres", listOf("l", "litre", "litres", "liter", "liters")),
    MILLILITRE(MILLI(NonSI.LITRE), "millilitres", listOf("ml", "millilitre", "milliliter", "millilitres", "milliliters")),
    CENTILITRE(CENTI(NonSI.LITRE), "centilitres", listOf("cl", "centilitre", "centilitres", "centiliter", "centiliters")),
    DECILITRE(DECI(NonSI.LITRE), "decilitres", listOf("dl", "decilitre", "decilitres", "deciliter", "deciliters")),
    // Double
    LIQUID_OUNCE(NonSI.OUNCE_LIQUID_US, "liquid ounces", listOf("liquid ounce", "liquid ounces", "fl oz", "fl. oz")),
    US_GALLON(NonSI.GALLON_LIQUID_US, "US gallons", listOf("gallon", "gallons", "gal")),
    CUP(NonSI.LITRE.times(0.236588), "cups", listOf("cup", "cups")),

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
                // Then we check insensitive mathes
                first.lowercase(Locale.ROOT) in it.ciNames
            } ?: if (second != null ) collection.find {
                // Then we check for double matches
                "$first $second".lowercase(Locale.ROOT) in it.ciNames
            } else null
        }
    }
}