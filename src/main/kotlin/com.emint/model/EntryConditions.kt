package com.emint.model

import com.emint.enum.ConditionType
import com.emint.enum.Segment
import com.emint.enum.Signal
import com.emint.enum.TimeFrame

data class EntryConditions(
    val conditionsType: ConditionType?,
    val signals: Signal?,
    val brokerSymbol: String?,
    val segment: Segment?,
    val liquidity: Boolean = false,
    val expression: String?,
    val timeFrame: TimeFrame?,
    val noOfCandles: Int = 1 // 1to15
)
