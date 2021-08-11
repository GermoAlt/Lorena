package com.buffer.lorena.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import javax.measure.converter.UnitConverter
import javax.measure.quantity.Area
import javax.measure.unit.NonSI
import javax.measure.unit.ProductUnit
import javax.measure.unit.SI
import javax.measure.unit.SI.*
import javax.measure.unit.Unit as JUnit

enum class Units(val unit: JUnit<*>, val printedName: String, vararg val ciNames: String) {
    // Base units
    KELVIN(SI.KELVIN, "K", "kelvin", "k"),
    KILOGRAM(SI.KILOGRAM, "kg", "kg", "kilogram", "kgs", "kilograms"),
    METRE(SI.METRE, "m", "metre", "meter", "meters", "metres", "m"),
    SECOND(SI.SECOND, "sec", "second", "seconds", "s"),

    // Derived units
    GRAM(SI.GRAM, "g", "gram", "grams", "g"),
    CELSIUS(SI.CELSIUS, "°C", "celsius", "°c", "°", "c"),
    // Double
    SQUARE_METRE(SI.SQUARE_METRE, "m²", "m²", "m2", "square meter", "square meters"),
    KILOMETRE(SI.KILOMETRE, "km", "km", "kilometre", "kilometres", "kilometer", "kilometers"),
    CENTIMETRE(SI.CENTIMETRE, "cm", "cm", "centimetre", "centimetres", "centimeter", "centimeters"),
    MILLIMETRE(SI.MILLIMETRE, "mm", "mm", "millimetre", "millimeters", "millimeter", "millimeters"),

    // Non SI
    FOOT(NonSI.FOOT, "ft", "ft", "foot", "feet"),
    // Double
    SQUARE_FOOT(ProductUnit<Area>(NonSI.FOOT.times(NonSI.FOOT)), "ft²", "ft2", "ft²", "sq ft", "sq. ft", "square feet"),
    YARD(NonSI.YARD, "yd", "yard", "yards", "yd"),
    INCH(NonSI.INCH, "in", "inch", "inches", "in", """""""),
    MILE(NonSI.MILE, "mi", "mile", "miles", "mi"),

    MINUTE(NonSI.MINUTE, "min", "min", "minute", "minutes"),
    HOUR(NonSI.HOUR, "hr", "hours", "hour", "hr", "h"),
    DAY(NonSI.DAY, "d", "days", "day", "d"),
    WEEK(NonSI.WEEK, "w", "weeks", "week", "w"),
    YEAR(NonSI.YEAR, "yr", "year", "years", "yr"),
    MONTH(NonSI.MONTH, "mths", "month", "months", "mo"),

    POUND(NonSI.POUND, "lbs", "pound", "pounds", "lb", "lbs"),
    OUNCE(NonSI.OUNCE, "oz", "ounce", "ounces", "oz"),
    TON(NonSI.METRIC_TON, "t", "ton", "tonne", "tons", "tonnes", "t"),
    // Double
    US_TON(NonSI.TON_US, "us t", "us ton", "us tons", "imperial ton", "imperial tons"),

    FAHRENHEIT(NonSI.FAHRENHEIT, "°F", "°F", "fahrenheit", "f"),

    KMH(NonSI.KILOMETRES_PER_HOUR, "km/h", "km/h", "kmh", "kph"),
    MPH(NonSI.MILES_PER_HOUR, "mph", "mph"),

    LITRE(NonSI.LITRE, "l", "l", "litre", "litres", "liter", "liters"),
    MILLILITRE(MILLI(NonSI.LITRE), "ml", "ml", "millilitre", "milliliter", "millilitres", "milliliters"),
    CENTILITRE(CENTI(NonSI.LITRE), "cl", "cl", "centilitre", "centilitres", "centiliter", "centiliters"),
    DECILITRE(DECI(NonSI.LITRE), "dl", "dl", "decilitre", "decilitres", "deciliter", "deciliters"),
    // Double
    LIQUID_OUNCE(NonSI.OUNCE_LIQUID_US, "fl. oz", "liquid ounce", "liquid ounces", "fl oz", "fl. oz"),
    US_GALLON(NonSI.GALLON_LIQUID_US, "gal", "gallon", "gallons", "gal"),
    CUP(NonSI.LITRE.times(0.236588), "cups", "cup", "cups"),

    // Jokes
    GIRLS(CUP.unit.divide(2), "girls", "girls", "girl"),
    JOHNE(INCH.unit.times(26), "JohnEs", "johne", "johnes", "john", "johns"),
    BANANA(CENTIMETRE.unit.times(13), "bananas", "banana", "bananas"),
    SCARAMUCCI(DAY.unit.times(10), "Mooches", "mooch", "mooches", "scaramucci", "scaramuccis"),
    SMOOT(CENTIMETRE.unit.times(170), "Smoots", "smoot", "smoots"),

    // According to journalistenheder.dk:
    ROUND_TOWERS(METRE.unit.times(34.8), "round towers", "round tower", "round towers"),
    MARATHONS(METRE.unit.times(42195), "marathons", "marathons", "marathon"),
    LOADED_JUMBO_JETS(KILOGRAM.unit.times(442000), "loaded jumbo jets", "loaded jumbo jets", "loaded jumbo jet"),
    USAIN_BOLT(KMH.unit.times(37.58), "Usain Bolt's average speed", "usain bolts", "usain bolts average speed"),
    REALTOR_STONE_THROW(METRE.unit.times(943), "Realtor's stone throws", "stone throw", "stone throws"),
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

        val AUTO_CONVERSION_NAMES: Set<String> = AUTO_CONVERSION.flatMap { it.ciNames.toSet() }.toSet()
        val ALL_NAMES: Set<String> = values().flatMap { it.ciNames.toSet() }.toSet()

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
        ): Units? = match(first.trim(), second?.trim(), AUTO_CONVERSION)

        fun matchAll(
            first: String,
            second: String? = null,
        ): Units? = match(first.trim(), second?.trim(), values())

        private fun splitToken(
            token: String,
            collection: Set<String>
        ): List<String> {
            return collection.find { token.contains("\\d$it\\b".toRegex()) }?.let {
                val first = token.substring(0 until token.length - it.length)
                if (first.toDoubleOrNull() != null) listOf(first, it) else listOf(token)
            } ?: listOf(token)
        }

        private fun match(
            first: String,
            second: String?,
            collection: Array<Units>
        ): Units? {
            // We will do two runs to check for matches
            return collection.find {
                // First we check insensitive matches
                first.lowercase(Locale.ROOT) in it.ciNames
            } ?: if (second != null ) collection.find {
                // Then we check for double matches
                "$first $second".lowercase(Locale.ROOT) in it.ciNames
            } else null
        }
    }
}