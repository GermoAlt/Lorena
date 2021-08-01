package com.buffer.lorena.utils

import javax.measure.unit.NonSI
import javax.measure.unit.SI
import javax.measure.unit.Unit as JUnit

enum class Units(val unit: JUnit<*>, ciNames: List<String>, vararg csNames: String) {
    // Base units
    AMPERE(SI.AMPERE, listOf("ampere"), "A"),
    KELVIN(SI.KELVIN, listOf("kelvin"), "K"),
    KILOGRAM(SI.KILOGRAM, listOf("kg, kilogram", "kgs", "kilograms")),
    METRE(SI.METRE, listOf("metre", "meter", "meters", "metres", "m")),
    SECOND(SI.SECOND, listOf("second", "seconds", "s")),

    // Derived units
    GRAM(SI.GRAM, listOf("gram", "grams", "g")),
    CELSIUS(SI.CELSIUS, listOf("celsius", "°c", "°"), "C"),
    SQUARE_METRE(SI.SQUARE_METRE, listOf("m²", "m2")),
    KILOMETRE(SI.KILOMETRE, listOf("km", "kilometre", "kilometres", "kilometer", "kilometers")),
    CENTIMETRE(SI.CENTIMETRE, listOf("cm", "centimetre", "centimetres", "centimeter", "centimeters")),
    MILLIMETRE(SI.MILLIMETRE, listOf("mm", "millimetre", "millimeters", "millimeter", "millimeters")),

    // Non SI
    FOOT(NonSI.FOOT, listOf("ft", "foot", "feet")),
    YARD(NonSI.YARD, listOf("yard", "yards", "yd")),
    INCH(NonSI.INCH, listOf("inch", "inches", "in", """"""")),
    MILE(NonSI.MILE, listOf("mile", "miles", "mi"))
    

}