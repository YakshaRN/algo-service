package com.emint.util

import com.emint.model.emint.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
@RedisHash
class RedisUtil(
    @Qualifier("redisTemplateForInstrumentDetail")private val redisTemplateForInstrumentDetail: RedisTemplate<String, Instruments>,
    @Qualifier("redisTemplateForLtp")private val redisTemplateForLtp: RedisTemplate<String, MarketFeedDto>,
    @Qualifier("redisTemplateForOpenOrders") private val redisTemplateForOpenOrders: RedisTemplate<String, OpenOrderCache>,
    @Qualifier("redisTemplateForPositionsInUi") private val redisTemplateForPositionsInUi: RedisTemplate<String, PositionsInUiRedisDto>
) {
    companion object {
        private val log = LoggerFactory.getLogger(RedisUtil::class.java)
    }

    fun getInstrumentData(key: String): Instruments? {
        val now = Instant.now().toEpochMilli()
        val instrumentData = redisTemplateForInstrumentDetail.opsForHash<String, Instruments>().get("INSTRUMENTS", key)
        log.info("time taken get Instrument Data = ${Instant.now().toEpochMilli() - now}")
        return instrumentData
    }

    fun getLtpData(key: String): MarketFeedDto? {
        val now = Instant.now().toEpochMilli()
        val ltp = redisTemplateForLtp.opsForHash<String, MarketFeedDto>().get("LTP", key)
        log.info("time taken get ltp for $key = ${Instant.now().toEpochMilli() - now}")
        return ltp
    }

    fun getUserPositionsForUi(key: String): List<PositionsInUi>? {
        log.info("Fetching data from Redis -> PositionsInUi $key ")
        val data = redisTemplateForPositionsInUi.opsForHash<String, PositionsInUiRedisDto>().get("PositionsInUi", key)
        return data?.data
    }

    fun getOpenOrders(userId: String): MutableList<OpenOrderCache>? {
        log.info("getting open orders from redis")
        val hkOrder = "OPEN_ORDERS:$userId"
        val now = Instant.now().toEpochMilli()
        val openOrder = redisTemplateForOpenOrders.opsForHash<String, OpenOrderCache>().values(hkOrder) as MutableList<OpenOrderCache>
        log.info("time taken to fetch open order for short sell = ${Instant.now().toEpochMilli() - now}")
        return openOrder
    }
}
