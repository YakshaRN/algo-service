package com.emint.dto

import com.emint.enums.ComparisonType
import com.emint.enums.PriceField

data class PriceFilter(
    val field: PriceField,
    val comparison: ComparisonType,
    val value: Double
)
