package com.emint.data.hmds

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(CandleID::class)
@Table(name = "minute_equity_candle_v2")
data class MinuteEQCandleV2(
    @Id
    var symbolId: Int? = null,

    @Id
    var timestamp: Int? = null,

    var open: Float? = null,

    var high: Float? = null,

    var low: Float? = null,

    var close: Float? = null,

    var tottrdqty: Long? = null
)
