package com.emint.util

import com.emint.constants.ApplicationConstants
import com.emint.data.optionchain.OptionChainApiResponse
import com.emint.model.emint.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.HashSet

@Repository
@RedisHash
class RedisUtil(
    @Qualifier("redisTemplateForInstrumentDetail")private val redisTemplateForInstrumentDetail: RedisTemplate<String, Instruments>,
    @Qualifier("redisTemplateForMarketFeed")private val redisTemplateForMarketFeed: RedisTemplate<String, MarketFeedDto>,
    @Qualifier("redisTemplateForOpenOrders") private val redisTemplateForOpenOrders: RedisTemplate<String, OpenOrderCache>,
    @Qualifier("redisTemplateForPositionsInUi") private val redisTemplateForPositionsInUi: RedisTemplate<String, PositionsInUiRedisDto>,
    @Qualifier("redisTemplateForStrikes") private val redisTemplateForStrikes: RedisTemplate<String, HashSet<Double>>,
    @Qualifier("redisTemplateForOptionChainResponse") private val redisTemplateForOptionChainResponse: RedisTemplate<String, OptionChainApiResponse>,
    @Qualifier("customRedisTemplateForOptionChain") private val customRedisTemplateForOptionChain: RedisTemplate<String, Instruments>,
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
        val ltp = redisTemplateForMarketFeed.opsForHash<String, MarketFeedDto>().get("LTP", key)
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

    fun getKeyValueForInstruments(key: String): com.emint.dto.instruments.Instruments? {
        val value = redisTemplateForInstrumentDetail.opsForHash<String, com.emint.dto.instruments.Instruments>().get(ApplicationConstants.INSTRUMENTS, key)
        log.info("getKeyValue key:$key")
        return value
    }

    fun getOptionChainTemplateForUnderlying(key: String): OptionChainApiResponse? {
        val value =
            redisTemplateForOptionChainResponse.opsForHash<String, OptionChainApiResponse>().get(
                ApplicationConstants.OPTION_CHAIN_RESPONSE,
                key,
            )
        log.info("getKeyValue key:$key")
        return value
    }

    fun getLtpForInstrument(key: String): com.emint.dto.MarketFeedDto? {
        var instrument: com.emint.dto.MarketFeedDto? = com.emint.dto.MarketFeedDto()
        log.info("Fetching instrument details for instrumentId $key ")
        try {
            instrument = redisTemplateForMarketFeed.opsForHash<String, com.emint.dto.MarketFeedDto>().get("LTP", key)
                ?: redisTemplateForMarketFeed.opsForHash<String, com.emint.dto.MarketFeedDto>().get("T-1-LTP", key)
        } catch (ex: Exception) {
            log.error("Instrument not found $ex")
        }
        return instrument
    }

    fun getKeyValueForOptionChain(key: String): com.emint.dto.instruments.Instruments? {
        val value = customRedisTemplateForOptionChain.opsForHash<String, com.emint.dto.instruments.Instruments>().get(ApplicationConstants.OPTION_CHAIN_MAP, key)
        log.info("getKeyValueForOptionChain key:$key")
        return value
    }

    fun getStrikesForUnderlying(key: String): HashSet<Double>? {
        log.info("Fetching getStrikesForUnderlying $key ")
        val strikeSet = redisTemplateForStrikes.opsForHash<String, HashSet<Double>>().get(ApplicationConstants.SYMBOL_STRIKE_PRICE_MAP, key)
        return strikeSet
    }

    fun getListOfOiDataFromMarketFeedDto(key: List<String>): List<com.emint.dto.MarketFeedDto> {
        val value = redisTemplateForMarketFeed.opsForHash<String, com.emint.dto.MarketFeedDto>().multiGet(ApplicationConstants.LTP, key)
        log.info("getKeyValueForOi key:$key")
        return value
    }
}
