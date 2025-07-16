package com.emint.repo

import com.emint.data.hmds.CandleID
import com.emint.data.hmds.MinuteEQCandleV2
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface MinuteFOCandlesV2Repo : JpaRepository<MinuteEQCandleV2, CandleID> {

//    @Query(
//        value = "select t from MinuteFOCandleV2 t where t.symbolId in :symbolIDs and timestamp >= :fromTS and timestamp < :toTS "
//    )
//    fun fetchAllCandlesWithinGivenTSForGivenSymbolIDs(symbolIDs: List<Int>, fromTS: Long, toTS: Long): List<MinuteFOCandleV2>

    @Query(
        value = "select t from MinuteEQCandleV2 t where t.symbolId in :symbolIDs and t.timestamp >= :fromTS and t.timestamp < :toTS "
    )
    fun fetchAllCandlesWithinGivenTSForGivenSymbolIDs(symbolIDs: List<Int>, fromTS: Long, toTS: Long): List<MinuteEQCandleV2>

    @Query(
        "select d from MinuteEQCandleV2 d where d.timestamp = :timestamp and d.symbolId in :symbolIDs"
    )
    fun fetchAllDataForGivenTimestampAndSymbolIDs(
        timestamp: Long,
        symbolIDs: Set<Int>
    ): List<MinuteEQCandleV2>

    @Query("select distinct d.symbolId from MinuteEQCandleV2 d where timestamp >= :fromTS and timestamp < :toTS")
    fun fetchAllSymbolIDsWithinGivenTS(fromTS: Long, toTS: Long): Set<Int>

    @Transactional
    @Modifying
    @Query(
        "delete from MinuteEQCandleV2 where timestamp >= :fromTS and timestamp < :toTS and symbolId not in :symbolIDs"
    )
    fun deleteCandlesBWGivenTSAndNotInGivenSymbolIDs(
        symbolIDs: Set<Int>,
        fromTS: Long,
        toTS: Long
    ): Int

    @Query(
        "select distinct symbol_id from minute_fo_candle_v2 " +
            "WHERE timestamp > :startTime and timestamp < :endTime",
        nativeQuery = true
    )
    fun getAllSymbolIDsForTheDay(startTime: Long, endTime: Long): Set<Int>
}
