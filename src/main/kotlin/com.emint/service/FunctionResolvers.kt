package com.emint.service

import org.springframework.stereotype.Service

@Service
class FunctionResolvers(
    private val marketFeedData: MarketFeedData
) {

    fun compareLtp(symbol: String, field: String, timeInterval: Int): Double {
        return marketFeedData.getMarketData(symbol, field, timeInterval)
    }

    fun resolveWD(a: Int, b: Int): Double = a + b * 2.0

    fun resolveWD(a: String, b: Int, c: Int): Double = b + c + a.length.toDouble()

    fun resolveMD(field: String, offset: Int): Double = 40.0 + offset

    fun resolveGD(field: String, offset: Int): Double = 60.0 + offset
}
