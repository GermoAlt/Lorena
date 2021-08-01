package com.buffer.lorena.utils

import java.util.*
import javax.measure.quantity.Area
import javax.measure.unit.NonSI
import javax.measure.unit.ProductUnit
import javax.measure.unit.SI
import javax.measure.unit.Unit as JUnit

enum class Units(val unit: JUnit<*>, val ciNames: List<String>, vararg val csNames: String) {
    // Base units
    KELVIN(SI.KELVIN, listOf("kelvin"), "K"),
    KILOGRAM(SI.KILOGRAM, listOf("kg, kilogram", "kgs", "kilograms")),
    METRE(SI.METRE, listOf("metre", "meter", "meters", "metres", "m")),
    SECOND(SI.SECOND, listOf("second", "seconds", "s")),

    // Derived units
    GRAM(SI.GRAM, listOf("gram", "grams", "g")),
    CELSIUS(SI.CELSIUS, listOf("celsius", "°c", "°"), "C"),
    // Double
    SQUARE_METRE(SI.SQUARE_METRE, listOf("m²", "m2", "square meter", "square meters")),
    KILOMETRE(SI.KILOMETRE, listOf("km", "kilometre", "kilometres", "kilometer", "kilometers")),
    CENTIMETRE(SI.CENTIMETRE, listOf("cm", "centimetre", "centimetres", "centimeter", "centimeters")),
    MILLIMETRE(SI.MILLIMETRE, listOf("mm", "millimetre", "millimeters", "millimeter", "millimeters")),

    // Non SI
    FOOT(NonSI.FOOT, listOf("ft", "foot", "feet")),
    // Double
    SQUARE_FOOT(ProductUnit<Area>(NonSI.FOOT.times(NonSI.FOOT)), listOf("ft2", "ft²", "sq ft", "sq. ft", "square feet")),
    YARD(NonSI.YARD, listOf("yard", "yards", "yd")),
    INCH(NonSI.INCH, listOf("inch", "inches", "in", """"""")),
    MILE(NonSI.MILE, listOf("mile", "miles", "mi")),

    MINUTE(NonSI.MINUTE, listOf("min", "minute", "minutes")),
    HOUR(NonSI.HOUR, listOf("hours", "hour", "hr", "h")),
    DAY(NonSI.DAY, listOf("days", "day", "d")),
    WEEK(NonSI.WEEK, listOf("weeks", "week", "w")),
    YEAR(NonSI.YEAR, listOf("year", "years", "yr")),
    MONTH(NonSI.MONTH, listOf("month", "months", "mo")),

    POUND(NonSI.POUND, listOf("pound", "pounds", "lb", "lbs")),
    OUNCE(NonSI.OUNCE, listOf("ounce", "ounces", "oz")),
    TON(NonSI.METRIC_TON, listOf("ton", "tonne", "tons", "tonnes", "t")),
    // Double
    US_TON(NonSI.TON_US, listOf("us ton", "us tons", "imperial ton", "imperial tons")),

    FAHRENHEIT(NonSI.FAHRENHEIT, listOf("°F", "fahrenheit"), "F"),

    KMH(NonSI.KILOMETRES_PER_HOUR, listOf("km/h", "kmh", "kph")),
    MPH(NonSI.MILES_PER_HOUR, listOf("mph")),

    LITRE(NonSI.LITRE, listOf("l", "litre", "litres", "liter", "liters")),
    MILLILITRE(NonSI.LITRE.divide(1000), listOf("ml", "millilitre", "milliliter", "millilitres", "milliliters")),
    CENTILITRE(NonSI.LITRE.divide(100), listOf("cl", "centilitre", "centilitres", "centiliter", "centiliters")),
    DECILITRE(NonSI.LITRE.divide(10), listOf("dl", "decilitre", "decilitres", "deciliter", "deciliters")),
    // Double
    LIQUID_OUNCE(NonSI.OUNCE_LIQUID_US, listOf("liquid ounce", "liquid ounces", "oz fl")),
    US_GALLON(NonSI.GALLON_LIQUID_US, listOf("gallon", "gallons", "gal")),

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
            OUNCE,
            US_GALLON
        )

        val AUTO_CONVERSION_NAMES: Set<String> = AUTO_CONVERSION.flatMap { it.ciNames + it.csNames }.toSet()
        val ALL_NAMES: Set<String> = values().flatMap { it.ciNames + it.csNames }.toSet()

        fun splitToken(
            token: String,
            collection: Array<String>
        ): List<String> {
            collection.find { token.endsWith(it) }?.let {
                listOf(token.substring(0..(token.length - it.length)))
            }
        }

        fun matchAuto(
            first: String,
            second: String?,
        ): Units? = match(first, second, AUTO_CONVERSION)

        fun matchAll(
            first: String,
            second: String?
        ): Units? = match(first, second, values())

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