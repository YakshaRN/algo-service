package com.emint.service.broker

import com.zerodhatech.kiteconnect.KiteConnect
import com.zerodhatech.models.HistoricalData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.Calendar
import java.util.Date

@Service
class KiteDataService(private val kiteConnect: KiteConnect) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        // Map of interval to maximum days per request
        private val INTERVAL_LIMITS = mapOf(
            "minute" to 60, "3minute" to 100, "5minute" to 100,
            "10minute" to 100, "15minute" to 200, "30minute" to 200,
            "60minute" to 400, "day" to 2000
        )
    }

    fun fetchHistoricalData(
        instrumentToken: String,
        fromDate: Date,
        toDate: Date,
        interval: String,
        continuous: Boolean,
        oi: Boolean = false
    ): List<HistoricalData> {
        log.info(
            "Fetching historical data for token: {}, from: {}, to: {}, interval: {}, continuous: {}, oi: {}",
            instrumentToken, fromDate, toDate, interval, continuous, oi
        )

        val maxDays = INTERVAL_LIMITS[interval]
            ?: throw IllegalArgumentException("Unsupported interval: $interval")

        val allData = mutableListOf<HistoricalData>()
        var currentFrom = fromDate

        while (currentFrom.before(toDate)) {
            val calendar = Calendar.getInstance()
            calendar.time = currentFrom
            calendar.add(Calendar.DAY_OF_YEAR, maxDays)
            var currentTo = calendar.time

            if (currentTo.after(toDate)) {
                currentTo = toDate
            }

            log.debug("Fetching chunk from {} to {}", currentFrom, currentTo)

            try {
                // The API call now correctly uses the 'oi' parameter
                val chunkData = kiteConnect.getHistoricalData(
                    currentFrom,
                    currentTo,
                    instrumentToken,
                    interval,
                    continuous,
                    oi
                )
                allData.addAll(chunkData as Collection<HistoricalData>)
            } catch (e: Exception) {
                log.error("Failed to fetch data chunk for token $instrumentToken from $currentFrom to $currentTo", e)
                break
            }
            currentFrom = currentTo
        }

        log.info("Total data points fetched for token {}: {}", instrumentToken, allData.size)
        return allData.sortedBy { it.timeStamp }
    }
}
