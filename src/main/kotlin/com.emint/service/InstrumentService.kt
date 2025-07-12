package com.emint.service

import com.emint.dto.InstrumentFilterRequest
import com.emint.dto.MarketFeedDto
import com.emint.dto.instruments.OptionTypeEnums
import com.emint.dto.optionchain.OptionChainDto
import com.emint.enums.ComparisonType
import com.emint.enums.PriceField
import com.emint.enums.SpotOrFuture
import com.emint.repo.StrategyDetailRepo
import com.emint.util.RedisUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.sound.midi.Instrument
import kotlin.math.abs

@Service
class InstrumentService(
    private val redisUtil: RedisUtil,
    private val strategyDetailRepo: StrategyDetailRepo
) {
    companion object {
        private val log = LoggerFactory.getLogger(InstrumentService::class.java)
    }

    fun findInstrumentsForEngine(strategyId: Long) {
        val strategy = strategyDetailRepo.findById(strategyId)
        // call findInstrumentsForEngine(InstrumentFilterRequest)
    }

    fun findInstrumentsForEngine(request: InstrumentFilterRequest): Any? {
        val underlying = request.underlying
        val optionChainKey = "$underlying ${request.expiry}"
        val responseTemplate =
            redisUtil.getOptionChainTemplateForUnderlying(optionChainKey)
                ?: throw Exception("Option chain not found for $optionChainKey")

        // Get LTP
        val ltpUnderlying =
            if (request.type == SpotOrFuture.SPOT_BASED) {
                redisUtil.getLtpForInstrument(underlying)
            } else {
                val futuresUnderlying = "$optionChainKey FUT"
                val futuresBrokerSymbol =
                    redisUtil.getKeyValueForOptionChain(futuresUnderlying)?.brokerSymbol
                        ?: throw Exception("Futures underlying not found: $futuresUnderlying")
                redisUtil.getLtpForInstrument(futuresBrokerSymbol)
            } ?: throw Exception("LTP not found")

        val isStrikeSearch =
            request.atmOffset != null ||
                (request.strikeRangeStart != null && request.strikeRangeEnd != null)
        val isPriceSearch = request.priceFilter != null

        if (isStrikeSearch && isPriceSearch) {
            throw IllegalArgumentException("Request must be either strike-based or price-based, not both.")
        }

        val listOfOptions =
            when (request.optionType) {
                OptionTypeEnums.CE -> responseTemplate.optionChain!!.mapNotNull { it.ce }
                OptionTypeEnums.PE -> responseTemplate.optionChain!!.mapNotNull { it.pe }
                else -> throw Exception("Unsupported option type")
            }

        val listOfBrokerSymbols = listOfOptions.map { it.brokerSymbol }
        val listOfLtp = redisUtil.getListOfOiDataFromMarketFeedDto(listOfBrokerSymbols)
        val symbolToFeed = listOfLtp.filterNotNull().associateBy { it.symbol }

        if (isStrikeSearch) {
            val toBeFoundStrike =
                request.atmOffset?.let {
                    getAtmStrikePrice(
                        underlying,
                        it,
                        ltpUnderlying.ltp.toDouble() / 100.0
                    )
                }

            val filteredByStrike =
                listOfOptions.filter { instrument ->
                    val strike = instrument.strikePrice ?: return@filter false
                    when {
                        toBeFoundStrike != null -> strike == toBeFoundStrike
                        request.strikeRangeStart != null && request.strikeRangeEnd != null ->
                            strike in request.strikeRangeStart..request.strikeRangeEnd
                        else -> false
                    }
                }

            return filteredByStrike
        }

        if (isPriceSearch) {
            val filter = request.priceFilter!!

            val filteredByPrice =
                if (filter.comparison == ComparisonType.NEAR) {
                    val instrumentWithValueList =
                        listOfOptions.mapNotNull { instrument ->
                            val feed = symbolToFeed[instrument.brokerSymbol] ?: return@mapNotNull null
                            instrument to getFieldValue(feed, filter.field)
                        }.sortedBy { it.second }

                    if (instrumentWithValueList.isEmpty()) return emptyList<Instrument>()

                    val closest =
                        instrumentWithValueList.minByOrNull { (_, value) ->
                            abs(value - filter.value)
                        }

                    listOfNotNull(closest?.first)
                } else {
                    var minDifference = Double.MAX_VALUE
                    var bestMatch: OptionChainDto? = null

                    listOfOptions.forEach { instrument ->
                        val feed = symbolToFeed[instrument.brokerSymbol] ?: return@forEach
                        val value = getFieldValue(feed, filter.field).toDouble()

                        when (filter.comparison) {
                            ComparisonType.GREATER -> {
                                if (value > filter.value) {
                                    val diff = value - filter.value
                                    if (diff < minDifference) {
                                        minDifference = diff
                                        bestMatch = instrument
                                    }
                                }
                            }
                            ComparisonType.LESSER -> {
                                if (value < filter.value) {
                                    val diff = filter.value - value
                                    if (diff < minDifference) {
                                        minDifference = diff
                                        bestMatch = instrument
                                    }
                                }
                            }
                            else -> {}
                        }
                    }
                    bestMatch?.let { listOf(it) } ?: emptyList()
                }

            return if (filter.comparison == ComparisonType.NEAR) {
                filteredByPrice.minByOrNull {
                    val feed = symbolToFeed[it.brokerSymbol]!!
                    abs(getFieldValue(feed, filter.field) - filter.value)
                }
            } else {
                filteredByPrice
            }
        }

        throw IllegalArgumentException("Request must contain either atmOffset/strikeRange or priceFilter.")
    }

    fun getFieldValue(
        dto: MarketFeedDto,
        field: PriceField
    ): Long {
        return when (field) {
            PriceField.LTP -> dto.ltp
            PriceField.OPEN -> dto.open.toLong()
            PriceField.HIGH -> dto.high.toLong()
            PriceField.LOW -> dto.low.toLong()
            PriceField.CLOSE -> dto.close.toLong()
            PriceField.OI -> dto.oi
            else -> throw IllegalArgumentException("Unsupported field: $field")
        }
    }

    private fun getAtmStrikePrice(
        underlying: String,
        atmOffset: Int,
        ltp: Double
    ): Double {
        val sortedStrikesForUnderlying = redisUtil.getStrikesForUnderlying(underlying)?.sorted()

        if (sortedStrikesForUnderlying.isNullOrEmpty()) return 0.0

        val index = sortedStrikesForUnderlying.binarySearch(ltp)

        val nearestIndex =
            when {
                index >= 0 -> index
                else -> {
                    val insertPoint = -index - 1
                    if (insertPoint == 0) {
                        0
                    } else if (insertPoint >= sortedStrikesForUnderlying.size) {
                        sortedStrikesForUnderlying.size - 1
                    } else {
                        val before = sortedStrikesForUnderlying[insertPoint - 1]
                        val after = sortedStrikesForUnderlying[insertPoint]
                        if (Math.abs(before - ltp) <= Math.abs(after - ltp)) insertPoint - 1 else insertPoint
                    }
                }
            }

        val finalIndex = nearestIndex + atmOffset
        return sortedStrikesForUnderlying.getOrNull(finalIndex) ?: 0.0
    }

    private fun strikeStep(): Int {
        return 100
    }
}
