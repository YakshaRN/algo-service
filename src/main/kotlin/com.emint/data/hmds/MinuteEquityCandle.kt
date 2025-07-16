package com.emint.dto

import jakarta.persistence.*

@Entity
@Table(name = "minute_equity_candle") // optional but safer
data class MinuteEquityCandle(
    @Id
    val id: Long,

    var timestamp: Long? = null,

    var symbol: String? = null,

    var open: Double? = null,

    var high: Double? = null,

    var low: Double? = null,

    var close: Double? = null,

    var tottrdqty: Double? = null
)
