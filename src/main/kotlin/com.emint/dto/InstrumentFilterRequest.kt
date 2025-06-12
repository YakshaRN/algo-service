package com.emint.dto

import com.emint.data.optionchain.OptionChainResponse
import com.emint.dto.instruments.OptionTypeEnums
import com.emint.enums.SpotOrFuture

data class InstrumentFilterRequest(
    val underlying: String,
    val expiry: String,
    val optionType: OptionTypeEnums,
    val type: SpotOrFuture? = null,
    val atmOffset: Int? = null,
    val strikePrice: Double? = null,
    val priceFilter: PriceFilter? = null,
    val deltaFilter: DeltaFilter? = null,
    val indicator: String? = null,
    val strikeRangeStart: Double? = null,
    val strikeRangeEnd: Double? = null,
    val optionChainResponse: OptionChainResponse?
)
