package com.emint.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class MarketFeedDto(
    var symbol: String,
    var localTs: Long,
    var seqnum: Long,
    var exchange: String?,
    var open: String,
    var high: String,
    var low: String,
    var close: String,
    var side: String?,
    var ttq: Long,
    var ttv: Double,
    var ltp: Long,
    var ltq: Long,
    var oi: Long,
    var changeOi: Long,
    var bidQty: Long,
    var askQty: Long,
    var validAsks: Long,
    var validBids: Long,
    var bids: List<List<Long>>?,
    var asks: List<List<Long>>?,
    var prevClose: String?,
    var prevOi: Long?,
    var lpp: String?,
    var ltt: Long?,
) {
    constructor() : this(
        "", 0, 0, "", "", "", "", "", null,
        0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, null, null, null, null,
    )

    constructor(symbol: String, localTs: Long, open: String, high: String, low: String, ltp: Long, close: String, ttq: Long) : this(
        symbol, localTs, 0, null, open, high, low, close, null, ttq, 0.0, ltp, 0, 0, 0, 0, 0, 0,
        0, null, null, null, null, null, null,
    )
}
