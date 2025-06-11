package com.emint.functionPattern

class StringFunctionMapper {

    companion object {
        val functionRegexMap: Map<String, Regex> = mapOf(
            "MD" to Regex("""MD\((\w+),\s*(-?\d+)\)\s*([><=!]=?)\s*(\d+)"""),
            "GD" to Regex("""GD\((\w+),\s*(-?\d+)\)\s*([><=!]=?)\s*(\d+)""")
        )
    }
}
