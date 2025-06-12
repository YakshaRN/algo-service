package com.emint.dto.instruments

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Instruments(
    @JsonProperty("id")
    val id: Long?,
    @JsonProperty("createdOn")
    var createdOn: Long?,
    @JsonProperty("updatedOn")
    var updatedOn: Long?,
    @JsonProperty("deletedOn")
    var deletedOn: Long?,
    @JsonProperty("instrumentId")
    var instrumentId: String?,
    @JsonProperty("tempInstrumentId")
    var tempInstrumentId: String?,
    @JsonProperty("brokerSymbol")
    var brokerSymbol: String,
    @JsonProperty("series")
    var series: String?,
    @JsonProperty("instrumentName")
    var instrumentName: String,
    @JsonProperty("instrumentSymbol")
    var instrumentSymbol: String,
    @JsonProperty("underlying")
    var underlying: String,
    @JsonProperty("dprH")
    var dprH: Double,
    @JsonProperty("dprL")
    var dprL: Double,
    @JsonProperty("segmentType")
    var segmentType: SegmentEnums?,
    @JsonProperty("exchange")
    var exchange: ExchangeEnums?,
    @JsonProperty("expiryDate")
    var expiryDate: String?,
    @JsonProperty("qbExpiryDate")
    var qbExpiryDate: String?,
    @JsonProperty("optionType")
    var optionType: OptionTypeEnums?,
    @JsonProperty("lotSize")
    var lotSize: Int,
    @JsonProperty("strikePrice")
    var strikePrice: Double?,
    @JsonProperty("isTradable")
    var isTradable: Boolean,
    @JsonProperty("isin")
    var isin: String?,
    @Transient
    @JsonIgnore
    @JsonProperty("instrumentSlug")
    val instrumentSlug: String? = null,
    @JsonProperty("priceBand")
    var priceBand: Int?,
    @JsonProperty("tickSize")
    var tickSize: Double?,
    @JsonProperty("freezeQty")
    var freezeQty: Long?,
)
