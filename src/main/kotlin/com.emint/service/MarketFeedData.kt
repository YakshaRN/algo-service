package com.emint.service

import com.emint.model.InstrumentDataRequest
import com.emint.model.InstrumentDataResponse
import com.emint.util.RedisUtil
import org.springframework.stereotype.Service

@Service
class MarketFeedData(
    private val redisUtil: RedisUtil,
    private val marketDataAdapter: MarketDataAdapter
) {
    fun fetch1MinData(instrumentDataRequest: InstrumentDataRequest): InstrumentDataResponse {
        return marketDataAdapter.fetchData1Min(instrumentDataRequest)
    }

    fun fetch1DayData(instrumentDataRequest: InstrumentDataRequest): InstrumentDataResponse {
        return marketDataAdapter.fetchData1day(instrumentDataRequest)
    }

    fun getMarketData(symbol: String, field: String, timeInterval: Int): Double {
        val feed = redisUtil.getLtpData(symbol)!!
        return when (field) {
            "open" -> { feed.open.toDouble() }
            "close" -> { feed.close.toDouble() }
            "high" -> { feed.high.toDouble() }
            "low" -> { feed.low.toDouble() }
            "oi" -> { feed.oi.toDouble() }
            "prevOi" -> { feed.prevOi!!.toDouble() }
            else -> { feed.prevClose!!.toDouble() }
        }
    }
}
