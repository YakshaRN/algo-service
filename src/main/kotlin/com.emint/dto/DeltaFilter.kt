package com.emint.dto

import com.emint.enums.ComparisonType

data class DeltaFilter(
    val comparison: ComparisonType,
    val value: Double
)
