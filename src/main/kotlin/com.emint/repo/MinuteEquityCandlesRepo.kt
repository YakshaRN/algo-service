package com.emint.repo

import com.emint.dto.MinuteEquityCandle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MinuteEquityCandlesRepo : JpaRepository<MinuteEquityCandle, Long> {

    @Query("SELECT m FROM MinuteEquityCandle m WHERE m.timestamp = :time AND m.symbol = :symbol")
    fun getCandleForSymbol(@Param("time") time: Long, @Param("symbol") symbol: String): MinuteEquityCandle?

//    @Query(
//        value = "select t from MinuteEquityCandle t where symbol in :symbols and timestamp >= :fromTS and timestamp < :toTS "
//    )
//    fun fetchAllCandlesWithinGivenTSForGivenSymbols(symbols: List<String>, fromTS: Long, toTS: Long): List<MinuteEquityCandle>
}
