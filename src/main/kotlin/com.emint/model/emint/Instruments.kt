package com.emint.model.emint

import com.emint.enum.Segment
import com.emint.type.Exchange
import com.emint.type.OptionType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Instruments(
    @JsonProperty("instrumentId") val instrumentId: String,
    @JsonProperty("brokerSymbol") val brokerSymbol: String,
    @JsonProperty("instrumentSymbol") val instrumentSymbol: String,
    @JsonProperty("underlying") val underlying: String,
    @JsonProperty("dprH") val dprH: Double,
    @JsonProperty("dprL") val dprL: Double,
    @JsonProperty("segmentType") val segmentType: Segment,
    @JsonProperty("exchange") val exchange: Exchange?,
    @JsonProperty("expiryDate") val expiryDate: String? = null,
    @JsonProperty("qbExpiryDate") var qbExpiryDate: String? = null,
    @JsonProperty("lotSize") val lotSize: Int,
    @JsonProperty("isTradable") val isTradable: Boolean?,
    @JsonProperty("strikePrice") val strikePrice: Double?,
    @JsonProperty("optionType") val optionType: OptionType?,
    @JsonProperty("isin") val isin: String? = null,
    @JsonProperty("series") val series: String? = null,
    @JsonProperty("freezeQty") val freezeQty: Long? = null,
    @JsonProperty("tickSize") val tickSize: Double
)
