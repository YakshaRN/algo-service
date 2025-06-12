package com.emint.data.optionchain

import com.emint.dto.instruments.ExchangeEnums
import com.emint.dto.instruments.OptionTypeEnums
import com.emint.dto.instruments.SegmentEnums
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class OptionChainDto(
    @JsonProperty("instrumentId")
    var instrumentId: String,
    @JsonProperty("instrumentSymbol")
    var instrumentSymbol: String,
    @JsonProperty("brokerSymbol")
    var brokerSymbol: String,
    @JsonProperty("segmentType")
    var segmentType: SegmentEnums?,
    @JsonProperty("exchange")
    var exchange: ExchangeEnums?,
    @JsonProperty("optionType")
    var optionType: OptionTypeEnums?,
    @JsonProperty("lotSize")
    var lotSize: Int,
    @JsonProperty("strikePrice")
    var strikePrice: Double?,
    @JsonProperty("ltp")
    var ltp: Long?
//    var oi: Long? = 0L,
//    var prevOi: Long? = 0L
)
