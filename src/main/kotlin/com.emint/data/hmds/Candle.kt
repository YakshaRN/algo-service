package com.emint.dto

data class Candle(
    val timestamp: Long,
    val symbol: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val tottrdqty: Double
)
